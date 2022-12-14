package com.gura.step04.file.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.gura.step04.file.dto.FileDto;
import com.gura.step04.file.service.FileService;

@Controller
public class FileController {
	@Autowired
	private FileService service;
	
	@RequestMapping("/file/list")
	public String list(HttpServletRequest request) {
		//보통은 ModelAndView 전달하지만 이번엔 예전 페이징처리 코드 사용 위해 request 받아 옴.
		//맘에 안들면 ModelAndView로 써도 상관 없다.(setAttribute 개신 addObject로 써도 무방하다 이말이야)
		service.getList(request);
		
		return "file/list";
	}
	
	@RequestMapping("/file/upload_form")
	public ModelAndView authUploadForm(HttpServletRequest request) {
		
		return new ModelAndView("file/upload_form");
	}
	
	//파일 업로드 요청처리
	@RequestMapping("/file/upload")
	public ModelAndView authUpload(FileDto dto, ModelAndView mView,
			HttpServletRequest request) {
		//aspect가 동작하기 위한세가지 조건 ModelAndView, auth..., HttpServletRequest
		/*
		 * FileDto 의 title에는 입력한 제목
		 * myFile 에는 업로드한 파일의 정보가 들어 있다.
		 */
		//service로 넘긴다.
		service.saveFile(dto, mView, request);
		mView.setViewName("file/upload");
		
		return mView;
	}
	
	@RequestMapping("/file/download")
	// file/download.do?num= 어쩌구~ 로 추출되는 
	public ModelAndView download(@RequestParam int num, ModelAndView mView) {
		service.getFileData(num, mView);
		// 응답을 할 bean 의 이름을 설정 
		mView.setViewName("fileDownView");
		// /WEB-INF/views/fileDownView.jsp 가 있나? 그건 아님
		// jsp는 html 응답을 위해 있는건데, 파일 데이터 응답 전문은 아님.
		// 그럼 넌 누구임?> 그냥 다운로드만 전문으로 해주는 객체(bean)임. 
		// FileDownView.java << 가 해준다.
		return mView;
	}
	
	//파일 삭제 요청 처리
	@RequestMapping("/file/delete")
	public ModelAndView authDelete(@RequestParam int num,
			ModelAndView mView, HttpServletRequest request) {
		
		service.deleteFile(num, request);
		
		mView.setViewName("redirect:/file/list.do");
		return mView;
	}
}











