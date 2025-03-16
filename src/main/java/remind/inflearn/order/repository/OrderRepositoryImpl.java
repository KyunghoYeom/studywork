package remind.inflearn.order.repository;


import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import remind.inflearn.order.dto.OrderSearch;
import remind.inflearn.order.dto.OrderSearchMDDto;
import remind.inflearn.order.dto.QOrderSearchMDDto;
import remind.inflearn.order.entity.OrderStatus;
import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.List;

import static remind.inflearn.delivery.QDelivery.delivery;
import static remind.inflearn.member.entity.QMember.*;
import static remind.inflearn.order.entity.QOrder.order;
import static org.springframework.util.StringUtils.hasText;

public class OrderRepositoryImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public OrderRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<OrderSearchMDDto> search(OrderSearch orderSearch) {
        return queryFactory
                .select(new QOrderSearchMDDto(
                        order.id,
                        order.member.name,
                        order.orderDate,
                        order.orderStatus,
                        order.delivery.address
                ))
                .from(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .where(
                        nameLike(orderSearch.getMemberName()),
                        statusEq(orderSearch.getOrderStatus())
                )
                .fetch();

    }

//    @Override
//    public Page<OrderSearchMDDto> searchPageSimple(OrderSearch orderSearch, Pageable pageable) {
//        QueryResults<OrderSearchMDDto> results = queryFactory
//                .select(new QOrderSearchMDDto(
//                        order.id,
//                        order.member.name,
//                        order.orderDate,
//                        order.orderStatus,
//                        order.delivery.address
//                ))
//                .from(order)
//                .join(order.member, member).fetchJoin()
//                .join(order.delivery, delivery).fetchJoin()
//                .where(
//                        nameLike(orderSearch.getMemberName()),
//                        statusEq(orderSearch.getOrderStatus())
//                )
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetchResults();
//        List<OrderSearchMDDto> content = results.getResults();
//        long total = results.getTotal();
//
//        return new PageImpl<>(content,pageable, total);
//
//
//
//    }

    @Override
    public Page<OrderSearchMDDto> searchPageComplex(OrderSearch orderSearch, Pageable pageable) {
        List<OrderSearchMDDto> content = queryFactory
                .select(new QOrderSearchMDDto(
                        order.id,
                        order.member.name,
                        order.orderDate,
                        order.orderStatus,
                        order.delivery.address
                ))
                .from(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .where(
                        nameLike(orderSearch.getMemberName()),
                        statusEq(orderSearch.getOrderStatus())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        int totalSize = queryFactory
                .select(new QOrderSearchMDDto(
                        order.id,
                        order.member.name,
                        order.orderDate,
                        order.orderStatus,
                        order.delivery.address
                ))
                .from(order)
                .join(order.member, member).fetchJoin()
                .join(order.delivery, delivery).fetchJoin()
                .where(
                        nameLike(orderSearch.getMemberName()),
                        statusEq(orderSearch.getOrderStatus())
                )
                .fetch().size();
        return new PageImpl<>(content,pageable, totalSize);



    }

    private BooleanExpression statusEq(OrderStatus orderStatus) {
        return orderStatus != null ? order.orderStatus.eq(orderStatus) : null;
    }

    private BooleanExpression nameLike(String memberName) {
        return hasText(memberName) ? order.member.name.eq(memberName) : null;
    }


}