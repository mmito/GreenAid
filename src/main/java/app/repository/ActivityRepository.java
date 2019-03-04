package app.repository;

import app.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Role, Long> {
}
