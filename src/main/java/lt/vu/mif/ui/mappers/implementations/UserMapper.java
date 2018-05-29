package lt.vu.mif.ui.mappers.implementations;

import lt.vu.mif.model.user.User;
import lt.vu.mif.ui.mappers.interfaces.IMapper;
import lt.vu.mif.ui.view.UserView;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component("userMapper")
public class UserMapper implements IMapper<User, UserView> {

    @Override
    public User toEntity(UserView view) {
        User entity = new User();

        entity.setId(view.getId());
        entity.setBlocked(view.isBlocked());
        entity.setEmail(view.getEmail());
        entity.setPassword(view.getPassword());
        entity.setRole(view.getRole());

        return entity;
    }

    @Override
    public List<User> toEntities(List<UserView> views) {
        if (CollectionUtils.isEmpty(views)) {
            return Collections.emptyList();
        }

        return views.stream().map(this::toEntity).collect(Collectors.toList());
    }

    @Override
    public UserView toView(User entity) {
        UserView view = new UserView();

        view.setId(entity.getId());
        view.setBlocked(entity.isBlocked());
        view.setEmail(entity.getEmail());
        view.setPassword(entity.getPassword());
        view.setRole(entity.getRole());

        return view;
    }

    @Override
    public List<UserView> toViews(List<User> entities) {
        if (CollectionUtils.isEmpty(entities)) {
            return Collections.emptyList();
        }

        return entities.stream().map(this::toView).collect(Collectors.toList());
    }
}