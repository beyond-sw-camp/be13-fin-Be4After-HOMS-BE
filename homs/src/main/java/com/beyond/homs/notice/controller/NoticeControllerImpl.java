package com.beyond.homs.notice.controller;

import com.beyond.homs.common.dto.ResponseDto;
import com.beyond.homs.notice.dto.CreateNoticeDto;
import com.beyond.homs.notice.dto.NoticeListDto;
import com.beyond.homs.notice.dto.NoticeResponseDto;
import com.beyond.homs.notice.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notice")
public class NoticeControllerImpl implements NoticeController {
    private final NoticeService noticeService;

    @Override
    @PostMapping("/create")
    public ResponseEntity<ResponseDto<NoticeResponseDto>> createNotice(
        @RequestBody CreateNoticeDto createNoticeDto){

        NoticeResponseDto notice = noticeService.createNotice(createNoticeDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                    new ResponseDto<>(
                            HttpStatus.CREATED.value(),
                            "공지사항이 성공적으로 생성되었습니다.",
                            notice
                ));
    }

    @Override
    @GetMapping("/")
    public ResponseEntity<ResponseDto<List<NoticeListDto>>> noticeList(){

        List<NoticeListDto> noticeList = noticeService.getNotices();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseDto<>(
                                HttpStatus.OK.value(),
                                "모든 공지사항을 불러왔습니다.",
                                noticeList
                        ));
    }
}
