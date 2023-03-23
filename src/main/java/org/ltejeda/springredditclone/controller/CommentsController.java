package org.ltejeda.springredditclone.controller;

import lombok.RequiredArgsConstructor;
import org.ltejeda.springredditclone.dto.CommentsDto;
import org.ltejeda.springredditclone.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentsController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Void> createComment(@RequestBody CommentsDto commentsDto){
        commentService.createComment(commentsDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(params = "postId")
    public ResponseEntity<List<CommentsDto>> getAllCommentsForPost(@RequestParam Long postId){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByPost(postId));
    }

    @GetMapping(params = "userName")
    public ResponseEntity<List<CommentsDto>> getAllCommentsByUser(@RequestParam String userName){
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getCommentsByUser(userName));
    }

}
