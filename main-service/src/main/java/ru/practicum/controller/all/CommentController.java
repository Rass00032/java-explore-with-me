package ru.practicum.controller.all;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ru.practicum.model.response.CommentDto;
import ru.practicum.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/events/{eventId}/comment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentController {

    CommentService commentService;

    @GetMapping
    public List<CommentDto> getCommentsToEvent(
            @PathVariable Integer eventId,
            @RequestParam(required = false, defaultValue = "0") Integer from,
            @RequestParam(required = false, defaultValue = "10") Integer size) {
        return commentService.getComments(eventId, from, size);
    }
}
