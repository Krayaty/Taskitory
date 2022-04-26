package de.krayadev.domain.valueobjects;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
@ToString
public final class IrrelevantUserData {

    @Column(length = 255)
    private String email;

    @Column(length = 255)
    private String email_constraint;

    @Column
    private final boolean email_verified = true;

    @Column
    private final boolean enabled = true;

    @Column(length = 255)
    private String federation_link;

    @Column(length = 255, nullable = false)
    @NonNull
    private String first_name;

    @Column(length = 255, nullable = false)
    @NonNull
    private String last_name;

    @Column
    private final String realm_id = "Taskitory-Realm";

    @Column
    private final long created_timestamp = System.currentTimeMillis();

    @Column(length = 255)
    private String service_account_client_link;

    @Column
    private final int not_before = 0;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        IrrelevantUserData that = (IrrelevantUserData) o;
        return email_verified == that.email_verified &&
                enabled == that.enabled &&
                created_timestamp == that.created_timestamp &&
                not_before == that.not_before &&
                Objects.equals(email, that.email) &&
                Objects.equals(email_constraint, that.email_constraint) &&
                Objects.equals(federation_link, that.federation_link) &&
                first_name.equals(that.first_name) &&
                last_name.equals(that.last_name) &&
                Objects.equals(realm_id, that.realm_id) &&
                Objects.equals(service_account_client_link, that.service_account_client_link);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, email_constraint, email_verified, enabled, federation_link, first_name, last_name,
                realm_id, created_timestamp, service_account_client_link, not_before);
    }
}
