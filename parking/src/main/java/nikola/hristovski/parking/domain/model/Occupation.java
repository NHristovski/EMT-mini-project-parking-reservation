package nikola.hristovski.parking.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nikola.hristovski.sharedkernel.domain.base.AbstractEntity;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "ocupation")
@AllArgsConstructor
@Getter
public class Occupation extends AbstractEntity<OccupationId> {

    @Version
    private Long version;

    @Embedded
    @AttributeOverride(name="id",column = @Column(name="user_id",nullable = false))
    private UserId userId;

    @Column(name = "from_instant")
    private Instant from;

    @Column(name = "to_instant")
    private Instant to;

    @Column(name = "canceled")
    private Boolean canceled;

    public Occupation() {
    }

    public Occupation(Instant from, UserId userId, Instant to, boolean canceled){
        super(OccupationId.randomId(OccupationId.class));
        this.from = from;
        this.to = to;
        this.canceled = canceled;
        this.userId = userId;
    }

    public Occupation(Instant from, UserId userId, Instant to){
        super(OccupationId.randomId(OccupationId.class));
        this.from = from;
        this.to = to;
        this.canceled = false;
        this.userId = userId;
    }

    public boolean isInterferingWith(Instant from, Instant to) {
        return from.isBefore(this.to) && to.isAfter(this.from);
    }

    public void cancel() {
        this.canceled = true;
    }

    public boolean isCanceled() { return canceled != null && canceled;}
}
