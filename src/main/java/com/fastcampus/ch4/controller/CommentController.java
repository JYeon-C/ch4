package com.fastcampus.ch4.controller;

import com.fastcampus.ch4.domain.CommentDto;
import com.fastcampus.ch4.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentService service;

//    {
//        "pcno":0,
//            "comment" : "hi"
//    }

    // 댓글을 수정하는 메서드
    @ResponseBody
    @PatchMapping("/comments/{cno}") // /comments/1?bno=1085 PATCH
    public ResponseEntity<String> modify(@PathVariable Integer cno ,@RequestBody CommentDto dto, HttpSession session) {
//        String commenter = (String)session.getAttribute("id"); // 지금은 로그인을 안함
        String commenter = "asdf";
        dto.setCommenter(commenter);
        dto.setCno(cno);
        System.out.println("dto = " + dto);

        try {
            if(service.modify(dto)!=1)
                throw new Exception("modify Failed");

            return new ResponseEntity<>("MOD_OK" ,HttpStatus.OK); // 200

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("MOD_ERR" ,HttpStatus.BAD_REQUEST); // 400
        }
    }



    // 댓글을 등록하는 메서드
    @ResponseBody
    @PostMapping("/comments") // /comments/1?bno=1085 <-- 삭제할 댓글 번호
    public ResponseEntity<String> write(@RequestBody CommentDto dto, Integer bno, HttpSession session) {
//        String commenter = (String)session.getAttribute("id"); // 지금은 로그인을 안함
        String commenter = "asdf";
        dto.setCommenter(commenter);
        dto.setBno(bno);
        System.out.println("dto = " + dto);

        try {
            if(service.write(dto)!=1)
                throw new Exception("Write Failed");

            return new ResponseEntity<>("WRT_OK" ,HttpStatus.OK); // 200

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>("WRT_ERR" ,HttpStatus.BAD_REQUEST); // 400
        }
    }


    // 지정된 댓글을 삭제하는 메서드
    @DeleteMapping("/comments/{cno}") // /comments/1?bno=1085 <-- 삭제할 댓글 번호
    @ResponseBody
    public ResponseEntity<String> remove(@PathVariable Integer cno, Integer bno, HttpSession session) {
//        String commenter = (String)session.getAttribute("id"); // 지금은 로그인을 안함
        String commenter = "asdf";

        try {
            int rowCnt = service.remove(cno,bno,commenter);

            if(rowCnt!=1)
                throw new Exception("Delete Failed");

            return new ResponseEntity<>("DEL_OK" ,HttpStatus.OK); // 200

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("DEL_ERR" ,HttpStatus.BAD_REQUEST); // 400

        }
    }

    // 지정딘 게시물의 모든 댓글을 가져오는 메서드
    @GetMapping("/comments") // /comments?bno=1080  GET
    @ResponseBody public ResponseEntity<List<CommentDto>> list(Integer bno) {
        List<CommentDto> list = null;
        try {
            list = service.getList(bno);
            return new ResponseEntity<List<CommentDto>>(list, HttpStatus.OK); // 200
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<List<CommentDto>>(HttpStatus.BAD_REQUEST); // 400
        }
    }
}
