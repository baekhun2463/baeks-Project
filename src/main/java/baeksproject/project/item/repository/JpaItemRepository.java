package baeksproject.project.item.repository;

import baeksproject.project.item.domain.Item;
import baeksproject.project.login.domain.member.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

import static baeksproject.project.item.domain.QItem.item;

@Repository
@Transactional
public class JpaItemRepository implements ItemRepository{

    private final EntityManager em;
    private final JPAQueryFactory query;

    public JpaItemRepository(EntityManager em) {
        this.em = em;
        this.query = new JPAQueryFactory(em);
    }

    public Page<Item> findPaginatedItems(ItemSearchCond itemSearch, int page, int size) {
        // 검색 조건에 따라 동적으로 쿼리 구성
        String qlString = "SELECT i FROM Item i WHERE 1=1 ";
        if (itemSearch.getItemName() != null && !itemSearch.getItemName().isEmpty()) {
            qlString += "AND i.itemName LIKE :itemName ";
        }
        if (itemSearch.getMaxPrice() != null) {
            qlString += "AND i.price <= :maxPrice ";
        }

        Query query = em.createQuery(qlString, Item.class);
        if (itemSearch.getItemName() != null && !itemSearch.getItemName().isEmpty()) {
            query.setParameter("itemName", "%" + itemSearch.getItemName() + "%");
        }
        if (itemSearch.getMaxPrice() != null) {
            query.setParameter("maxPrice", itemSearch.getMaxPrice());
        }

        query.setFirstResult(page * size);
        query.setMaxResults(size);

        List<Item> items = query.getResultList();

        // 전체 아이템 수 조회
        String countQlString = "SELECT COUNT(i) FROM Item i WHERE 1=1 ";
        if (itemSearch.getItemName() != null && !itemSearch.getItemName().isEmpty()) {
            countQlString += "AND i.itemName LIKE :itemName ";
        }
        if (itemSearch.getMaxPrice() != null) {
            countQlString += "AND i.price <= :maxPrice ";
        }

        Query countQuery = em.createQuery(countQlString);
        if (itemSearch.getItemName() != null && !itemSearch.getItemName().isEmpty()) {
            countQuery.setParameter("itemName", "%" + itemSearch.getItemName() + "%");
        }
        if (itemSearch.getMaxPrice() != null) {
            countQuery.setParameter("maxPrice", itemSearch.getMaxPrice());
        }

        long totalItems = (long) countQuery.getSingleResult();

        // 페이지 정보 생성 및 반환
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(items, pageable, totalItems);
    }


    public long countItems() {
        return em.createQuery("SELECT COUNT(i) FROM Item i", Long.class)
                .getSingleResult();
    }

    @Override
    public Item saveItemWithMember(Long memberId, Item item) {
        Member member = em.find(Member.class, memberId);
        item.setMember(member);
        em.persist(item);
        return item;
    }

    @Override
    public Item save(Item item) {
        em.persist(item);
        return item;
    }

    @Override
    public void update(Long itemId, ItemUpdateDto updateParam) {
        Item findItem = findById(itemId).orElseThrow();
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
        findItem.setImagePath(updateParam.getImagePath());
    }

    @Override
    public Optional<Item> findById(Long id) {
        Item item = em.find(Item.class, id);
        return Optional.ofNullable(item);
    }



    @Override
    public List<Item> findAll(ItemSearchCond cond) {
        String itemName = cond.getItemName();
        Integer maxPrice = cond.getMaxPrice();

        List<Item> result = query
                .select(item)
                .from(item)
                .where(likeItemName(itemName), maxPrice(maxPrice))
                .fetch();

        return result;
    }


    @Override
    public List<Item> findByMemberId(Long memberId) {
        return em.createQuery("SELECT i FROM Item i WHERE i.member.id = :memberId", Item.class)
                .setParameter("memberId", memberId)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Item item = em.find(Item.class, id);
        if (item != null) {
            em.remove(item);
        } else {
            throw new RuntimeException("Item not found");
        }
    }


    private BooleanExpression likeItemName(String itemName) {
        if(StringUtils.hasText(itemName)) {
            return item.itemName.like("%" + itemName + "%");
        }
        return null;
    }

    private BooleanExpression maxPrice(Integer maxPrice) {
        if(maxPrice != null) {
            return item.price.loe(maxPrice);
        }
        return null;
    }
}