package pl.java.springpetclinic.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Override
    @Query("select o from Owner o join fetch o.pets where o.id =:id")
    Optional<Owner> findById(Long id);

    boolean existsByPhoneNumber(String phoneNumber);
}
