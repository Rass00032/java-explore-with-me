package ru.practicum.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.entity.Comment;
import ru.practicum.entity.Event;
import ru.practicum.entity.User;
import ru.practicum.exception.NotFoundException;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.model.response.CommentDto;
import ru.practicum.storage.CommentRepository;
import ru.practicum.storage.EventRepository;
import ru.practicum.storage.UserRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CommentService {

    CommentRepository commentRepository;
    CommentMapper commentMapper;
    UserRepository userRepository;
    EventRepository eventRepository;

    public CommentDto addCommentToEvent(CommentDto commentDto, Integer userId, Integer eventId) {
        Comment comment = commentMapper.toComment(commentDto);
        User user = userRepository.findById(userId).orElseThrow(NotFoundException::new);
        Event event = eventRepository.findById(eventId).orElseThrow(NotFoundException::new);
        comment.setAuthor(user);
        comment.setEvent(event);
        comment.setCreated(LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS));
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public CommentDto changeComment(Integer userId, Integer eventId, Integer commentId,
                                    CommentDto commentDto) {
        Comment comment = commentRepository.findByIdAndEvent_IdAndAuthor_Id(commentId, eventId, userId)
                .orElseThrow(NotFoundException::new);
        commentMapper.updateComment(comment, commentDto);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    public CommentDto changeComment(Integer eventId, Integer commentId, CommentDto commentDto) {
        Comment comment = commentRepository.findByIdAndEvent_Id(commentId, eventId).orElseThrow(NotFoundException::new);
        commentMapper.updateComment(comment, commentDto);
        commentRepository.save(comment);
        return commentMapper.toCommentDto(comment);
    }

    @Transactional
    public void deleteComment(Integer eventId, Integer commentId) {
        if (!commentRepository.existsByIdAndEvent_Id(commentId, eventId)) {
            throw new NotFoundException("Данный комментарий у события с id " + eventId + " отсутствует");
        }
        commentRepository.deleteByIdAndEvent_Id(commentId, eventId);
    }

    @Transactional
    public void deleteComment(Integer userId, Integer eventId, Integer commentId) {
        if (!commentRepository.existsByIdAndEvent_IdAndAuthor_Id(commentId, eventId, userId)) {
            throw new NotFoundException("У пользователя с id " + userId + " отсутствует данный комментарий");
        }
        commentRepository.deleteByIdAndEvent_IdAndAuthor_Id(commentId, eventId, userId);
    }

    public List<CommentDto> getComments(Integer eventId, Integer from, Integer size) {
        List<Comment> comments = commentRepository.findAllByEvent_Id(eventId, PageRequest.of(from / size, size))
                .getContent();
        return commentMapper.toCommentDtos(comments);
    }
}
