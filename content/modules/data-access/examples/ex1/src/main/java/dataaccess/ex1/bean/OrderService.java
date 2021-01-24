package dataaccess.ex1.bean;

import dataaccess.ex1.entity.Customer;
import dataaccess.ex1.entity.Order;
import io.jmix.core.DataManager;
import io.jmix.core.Sort;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.querycondition.PropertyCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
public class OrderService {

    @Autowired
    private DataManager dataManager;

    // tag::load-by-query[]
    List<Order> loadOrdersByProduct(String productName) {
        return dataManager.load(Order.class)
                .query("from sample_Order o, sample_OrderLine l " +
                        "where l.order = o and l.product.name = ?1", productName)
                .list();
    }
    // end::load-by-query[]

    // tag::load-values[]
    String getCustomerPurchases(LocalDate toDate) {
        List<KeyValueEntity> kvEntities = dataManager.loadValues(
                "select o.customer, sum(o.amount) from sample_Order o " +
                        "where o.date >= :date group by o.customer")
                .store("main")                      // <1>
                .properties("customer", "sum")      // <2>
                .parameter("date", toDate)
                .list();

        StringBuilder sb = new StringBuilder();
        for (KeyValueEntity kvEntity : kvEntities) {
            Customer customer = kvEntity.getValue("customer");  // <3>
            BigDecimal sum = kvEntity.getValue("sum");          // <3>
            sb.append(customer.getName()).append(" : ").append(sum);
        }
        return sb.toString();
    }
    // end::load-values[]

    // tag::load-value[]
    BigDecimal getTotal(LocalDate toDate) {
        return dataManager.loadValue(
                    "select sum(o.amount) from sample_Order o where o.date >= :date",
                    BigDecimal.class    // <1>
                )
                .store("main")          // <2>
                .parameter("date", toDate)
                .one();
    }
    // end::load-value[]

    // tag::load-by-condition[]
    List<Order> loadByCondition() {
        return dataManager.load(Order.class)
                .condition(PropertyCondition.contains("customer.email", "@company.com"))
                .list();
    }
    // end::load-by-condition[]

    // tag::load-sorted[]
    List<Order> loadSorted() {
        return dataManager.load(Order.class)
                .all()
                .sort(Sort.by("customer.name"))
                .list();
    }
    // end::load-sorted[]

}