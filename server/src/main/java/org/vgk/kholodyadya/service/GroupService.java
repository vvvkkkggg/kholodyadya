package org.vgk.kholodyadya.service;

import org.springframework.stereotype.Service;
import org.vgk.kholodyadya.entity.Group;
import org.vgk.kholodyadya.entity.GroupRelation;
import org.vgk.kholodyadya.entity.User;
import org.vgk.kholodyadya.exceptions.InsufficientPermissions;
import org.vgk.kholodyadya.exceptions.NonExistentGroupException;
import org.vgk.kholodyadya.exceptions.NonExistentUserIdException;
import org.vgk.kholodyadya.repository.GroupRelationRepository;
import org.vgk.kholodyadya.repository.GroupRepository;
import org.vgk.kholodyadya.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
    private final GroupRepository groupRepository;
    private final GroupRelationRepository groupRelationRepository;
    private final UserRepository userRepository;

    public GroupService(GroupRepository groupRepository, GroupRelationRepository groupRelationRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.groupRelationRepository = groupRelationRepository;
        this.userRepository = userRepository;
    }

    public Group createGroup(Group group, int userId) {
        Group createdGroup = groupRepository.save(group);
        groupRelationRepository.save(new GroupRelation(
                new GroupRelation.GroupRelationId(createdGroup.getId(), userId),
                "admin"
        ));
        return createdGroup;
    }

    public List<Group> getUserGroups(int userId) {
        List<GroupRelation> foundGroups = groupRelationRepository.findByRelationId_UserId(userId);
        return foundGroups.stream().map(relation -> groupRepository.findById(relation.getRelationId().getGroupId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    public boolean addInGroup(int groupId, int adminUserId, String usernameToAdd) throws InsufficientPermissions, NonExistentGroupException, NonExistentUserIdException {
        Optional<GroupRelation> adminRelation = groupRelationRepository.findByRelationId(
                new GroupRelation.GroupRelationId(groupId, adminUserId)
        );
        if (adminRelation.isEmpty()) {
            throw new NonExistentGroupException("non existent group");
        }
        if (!adminRelation.get().getRole().equals("admin")) {
            throw new InsufficientPermissions("only admins can add in group");
        }

        User userToAdd = userRepository.findByUsername(usernameToAdd);
        if (userToAdd == null) {
            throw new NonExistentUserIdException("non existent user");
        }

        groupRelationRepository.save(new GroupRelation(new GroupRelation.GroupRelationId(groupId, userToAdd.getId()), "member"));
        return true;
    }
}