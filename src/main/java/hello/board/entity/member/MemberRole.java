package hello.board.entity.member;

public enum MemberRole {
    ADMIN("관리자"), USER("회원");

    private final String description;

    MemberRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
