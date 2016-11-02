package guru.springframework.service.mapservices;

import guru.springframework.domain.DomainObject;
import guru.springframework.domain.security.Role;
import guru.springframework.service.RoleService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("map")
public class RoleServiceImpl extends AbstractMapService implements RoleService {

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public Role getById(Integer id) {
        return (Role) super.getById(id);
    }

    @Override
    public Role saveOrUpdate(Role role) {
        return (Role) super.saveOrUpdate(role);
    }

    @Override
    public void delete(Integer id) {
        super.delete(id);
    }

}
