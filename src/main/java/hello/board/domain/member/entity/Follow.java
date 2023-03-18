package hello.board.domain.member.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="fromMember")
    private Member fromMember;

    @ManyToOne
    @JoinColumn(name="toMember")
    private Member toMember;

    public Follow(Member fromMember, Member toMember) {
        this.fromMember = fromMember;
        this.toMember = toMember;
    }


}
