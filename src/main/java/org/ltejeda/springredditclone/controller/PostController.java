package org.ltejeda.springredditclone.controller;

import lombok.AllArgsConstructor;
import org.ltejeda.springredditclone.dto.PostRequest;
import org.ltejeda.springredditclone.dto.PostResponse;
import org.ltejeda.springredditclone.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<Void> createPost(@RequestBody PostRequest postRequest){
        postService.save(postRequest);
        return new ResponseEntity<>(CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PostResponse>> getAllPost(){
        return ResponseEntity.status(OK).body(postService.getAllPost());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> getPost(@PathVariable Long id){
        return ResponseEntity.status(OK).body(postService.getPost(id));
    }

    @GetMapping("by-subreddit/{id}")
    public ResponseEntity<List<PostResponse>> getPostsBySubreddit(@PathVariable Long id){
        return ResponseEntity.status(OK).body(postService.getPostsBySubreddit(id));
    }

    @GetMapping("by-user/{username}")
    private ResponseEntity<List<PostResponse>> getPostsByUsername(@PathVariable String username){
        return ResponseEntity.status(OK).body(postService.getPostsByUsername(username));
    }

}
