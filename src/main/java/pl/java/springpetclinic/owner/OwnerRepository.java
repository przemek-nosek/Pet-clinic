package pl.java.springpetclinic.owner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    @Override
    @Query("select o from Owner o join fetch o.pets where o.id =:id")
    Optional<Owner> findById(Long id);

    boolean existsByPhoneNumber(String phoneNumber);

    @Query("update Owner o set o.firstName =:firstName, o.lastName=:lastName where o.id=:id")
    @Modifying
    void updateFirstnameAndLastname(Long id, String firstName, String lastName);

    @Query("update Owner o set o.phoneNumber=:phoneNumber where o.id=:id")
    @Modifying
    void updatePhoneNumber(Long id, String phoneNumber);

}
