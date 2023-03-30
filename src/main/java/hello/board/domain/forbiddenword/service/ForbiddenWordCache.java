package hello.board.domain.forbiddenword.service;

import hello.board.domain.comment.entity.Comment;
import hello.board.domain.forbiddenword.entity.ForbiddenWord;
import hello.board.domain.post.entity.Post;
import hello.board.exception.badrequest.AlreadyHaveWordBadRequestException;
import hello.board.exception.badrequest.ContainForbiddenWordBadRequestException;

import java.util.Set;

public class ForbiddenWordCache {

    private static Set<String> forbiddenWords;

    public static Set<String> getForbiddenWords() {
        return forbiddenWords;
    }

    public static void setForbiddenWords(Set<String> forbiddenWords) {
        ForbiddenWordCache.forbiddenWords = forbiddenWords;
    }

    public static void addForbiddenWord(ForbiddenWord forbiddenWord) {
        forbiddenWords.add(forbiddenWord.getWord());
    }

    public static void checkAlreadyHave(String word) {
        if (forbiddenWords.contains(word)) {
            throw new AlreadyHaveWordBadRequestException();
        }
    }

    public static boolean checkPostForbiddenWord(Post post) {
        String postTitle = post.getTitle();
        String postContent = post.getContent();

        for (String forbiddenWord : forbiddenWords) {
            if (postTitle.contains(forbiddenWord))
                return true;
            if (postContent.contains(forbiddenWord))
                return true;
        }
        return false;
    }

    public static void checkCommentForbiddenWord(Comment comment) {
        String commentContent = comment.getContent();
        for (String forbiddenWord : forbiddenWords) {
            if (commentContent.contains(forbiddenWord))
                throw new ContainForbiddenWordBadRequestException();
        }
    }
}
