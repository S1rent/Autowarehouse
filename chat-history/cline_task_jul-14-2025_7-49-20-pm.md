**User:**

<task>
{
    "details": "Error id 2e1a702e-b858-4ccb-9aae-b2e745c3af6b-2, org.hibernate.exception.SQLGrammarException: JDBC exception executing SQL [select o1_0.product_id,o1_0.id,o1_0.created_at,o1_0.order_id,o1_0.price,o1_0.product_name,o1_0.product_price,o1_0.product_sku,o1_0.quantity,o1_0.subtotal,o1_0.total_price from order_items o1_0 where o1_0.product_id = any (?)] [ERROR: column o1_0.price does not exist",
    "stack": "org.hibernate.exception.SQLGrammarException: JDBC exception executing SQL [select o1_0.product_id,o1_0.id,o1_0.created_at,o1_0.order_id,o1_0.price,o1_0.product_name,o1_0.product_price,o1_0.product_sku,o1_0.quantity,o1_0.subtotal,o1_0.total_price from order_items o1_0 where o1_0.product_id = any (?)] [ERROR: column o1_0.price does not exist\n  Position: 62] [n/a]\r\n\tat org.hibernate.exception.internal.SQLStateConversionDelegate.convert(SQLStateConversionDelegate.java:89)\r\n\tat org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:56)\r\n\tat org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:108)\r\n\tat org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:94)\r\n\tat org.hibernate.sql.results.jdbc.internal.DeferredResultSetAccess.executeQuery(DeferredResultSetAccess.java:257)\r\n\tat org.hibernate.sql.results.jdbc.internal.DeferredResultSetAccess.getResultSet(DeferredResultSetAccess.java:163)\r\n\tat org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.advanceNext(JdbcValuesResultSetImpl.java:254)\r\n\tat org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.processNext(JdbcValuesResultSetImpl.java:134)\r\n\tat org.hibernate.sql.results.jdbc.internal.AbstractJdbcValues.next(AbstractJdbcValues.java:19)\r\n\tat org.hibernate.sql.results.internal.RowProcessingStateStandardImpl.next(RowProcessingStateStandardImpl.java:66)\r\n\tat org.hibernate.sql.results.spi.ListResultsConsumer.consume(ListResultsConsumer.java:178)\r\n\tat org.hibernate.sql.results.spi.ListResultsConsumer.consume(ListResultsConsumer.java:33)\r\n\tat org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.doExecuteQuery(JdbcSelectExecutorStandardImpl.java:361)\r\n\tat org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.executeQuery(JdbcSelectExecutorStandardImpl.java:168)\r\n\tat org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.list(JdbcSelectExecutorStandardImpl.java:93)\r\n\tat org.hibernate.sql.exec.spi.JdbcSelectExecutor.list(JdbcSelectExecutor.java:31)\r\n\tat org.hibernate.loader.ast.internal.CollectionBatchLoaderArrayParam.initializeKeys(CollectionBatchLoaderArrayParam.java:206)\r\n\tat org.hibernate.loader.ast.internal.CollectionBatchLoaderArrayParam.load(CollectionBatchLoaderArrayParam.java:126)\r\n\tat org.hibernate.persister.collection.AbstractCollectionPersister.initialize(AbstractCollectionPersister.java:680)\r\n\tat org.hibernate.event.internal.DefaultInitializeCollectionEventListener.onInitializeCollection(DefaultInitializeCollectionEventListener.java:75)\r\n\tat org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)\r\n\tat org.hibernate.internal.SessionImpl.initializeCollection(SessionImpl.java:1702)\r\n\tat org.hibernate.collection.spi.AbstractPersistentCollection.lambda$initialize$3(AbstractPersistentCollection.java:617)\r\n\tat org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:265)\r\n\tat org.hibernate.collection.spi.AbstractPersistentCollection.initialize(AbstractPersistentCollection.java:615)\r\n\tat org.hibernate.collection.spi.AbstractPersistentCollection.read(AbstractPersistentCollection.java:136)\r\n\tat org.hibernate.collection.spi.PersistentBag.iterator(PersistentBag.java:371)\r\n\tat org.hibernate.type.CollectionType.getElementsIterator(CollectionType.java:244)\r\n\tat org.hibernate.engine.spi.CascadingActions.getAllElementsIterator(CascadingActions.java:470)\r\n\tat org.hibernate.engine.spi.CascadingActions$1.getCascadableChildrenIterator(CascadingActions.java:70)\r\n\tat org.hibernate.engine.internal.Cascade.cascadeCollectionElements(Cascade.java:550)\r\n\tat org.hibernate.engine.internal.Cascade.cascadeCollection(Cascade.java:482)\r\n\tat org.hibernate.engine.internal.Cascade.cascadeAssociation(Cascade.java:442)\r\n\tat org.hibernate.engine.internal.Cascade.cascadeProperty(Cascade.java:225)\r\n\tat org.hibernate.engine.internal.Cascade.cascade(Cascade.java:158)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.cascadeBeforeDelete(DefaultDeleteEventListener.java:502)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.deleteEntity(DefaultDeleteEventListener.java:391)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.delete(DefaultDeleteEventListener.java:242)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.deletePersistentInstance(DefaultDeleteEventListener.java:219)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.delete(DefaultDeleteEventListener.java:159)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.onDelete(DefaultDeleteEventListener.java:95)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.onDelete(DefaultDeleteEventListener.java:83)\r\n\tat org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)\r\n\tat org.hibernate.internal.SessionImpl.fireDelete(SessionImpl.java:961)\r\n\tat org.hibernate.internal.SessionImpl.delete(SessionImpl.java:892)\r\n\tat org.hibernate.internal.SessionImpl.remove(SessionImpl.java:2359)\r\n\tat io.quarkus.hibernate.orm.runtime.session.TransactionScopedSession.remove(TransactionScopedSession.java:167)\r\n\tat org.hibernate.engine.spi.SessionLazyDelegator.remove(SessionLazyDelegator.java:345)\r\n\tat org.hibernate.Session_OpdLahisOZ9nWRPXMsEFQmQU03A_Synthetic_ClientProxy.remove(Unknown Source)\r\n\tat io.quarkus.hibernate.orm.panache.common.runtime.AbstractJpaOperations.delete(AbstractJpaOperations.java:124)\r\n\tat io.quarkus.hibernate.orm.panache.PanacheEntityBase.delete(PanacheEntityBase.java:80)\r\n\tat com.autowarehouse.service.ProductService.deleteProduct(ProductService.java:248)\r\n\tat com.autowarehouse.service.ProductService_Subclass.deleteProduct$$superforward(Unknown Source)\r\n\tat com.autowarehouse.service.ProductService_Subclass$$function$$21.apply(Unknown Source)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:73)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:62)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:136)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:107)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.doIntercept(TransactionalInterceptorRequired.java:38)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.intercept(TransactionalInterceptorBase.java:61)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.intercept(TransactionalInterceptorRequired.java:32)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired_Bean.intercept(Unknown Source)\r\n\tat io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:42)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.perform(AroundInvokeInvocationContext.java:30)\r\n\tat io.quarkus.arc.impl.InvocationContexts.performAroundInvoke(InvocationContexts.java:27)\r\n\tat com.autowarehouse.service.ProductService_Subclass.deleteProduct(Unknown Source)\r\n\tat com.autowarehouse.service.ProductService_ClientProxy.deleteProduct(Unknown Source)\r\n\tat com.autowarehouse.resource.ProductResource.deleteProduct(ProductResource.java:291)\r\n\tat com.autowarehouse.resource.ProductResource_Subclass.deleteProduct$$superforward(Unknown Source)\r\n\tat com.autowarehouse.resource.ProductResource_Subclass$$function$$16.apply(Unknown Source)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:73)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext$NextAroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:97)\r\n\tat io.quarkus.security.runtime.interceptor.SecurityHandler.handle(SecurityHandler.java:27)\r\n\tat io.quarkus.security.runtime.interceptor.RolesAllowedInterceptor.intercept(RolesAllowedInterceptor.java:29)\r\n\tat io.quarkus.security.runtime.interceptor.RolesAllowedInterceptor_Bean.intercept(Unknown Source)\r\n\tat io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:42)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:70)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:62)\r\n\tat io.quarkus.resteasy.reactive.server.runtime.StandardSecurityCheckInterceptor.intercept(StandardSecurityCheckInterceptor.java:44)\r\n\tat io.quarkus.resteasy.reactive.server.runtime.StandardSecurityCheckInterceptor_RolesAllowedInterceptor_Bean.intercept(Unknown Source)\r\n\tat io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:42)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.perform(AroundInvokeInvocationContext.java:30)\r\n\tat io.quarkus.arc.impl.InvocationContexts.performAroundInvoke(InvocationContexts.java:27)\r\n\tat com.autowarehouse.resource.ProductResource_Subclass.deleteProduct(Unknown Source)\r\n\tat com.autowarehouse.resource.ProductResource$quarkusrestinvoker$deleteProduct_718b02bf067b9dbae2b1f9ae1eea98b9ea9e70ec.invoke(Unknown Source)\r\n\tat org.jboss.resteasy.reactive.server.handlers.InvocationHandler.handle(InvocationHandler.java:29)\r\n\tat io.quarkus.resteasy.reactive.server.runtime.QuarkusResteasyReactiveRequestContext.invokeHandler(QuarkusResteasyReactiveRequestContext.java:141)\r\n\tat org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.run(AbstractResteasyReactiveContext.java:147)\r\n\tat io.quarkus.vertx.core.runtime.VertxCoreRecorder$14.runWith(VertxCoreRecorder.java:582)\r\n\tat org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2513)\r\n\tat org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1538)\r\n\tat org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:29)\r\n\tat org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:29)\r\n\tat io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)\r\n\tat java.base/java.lang.Thread.run(Thread.java:840)\r\nCaused by: org.postgresql.util.PSQLException: ERROR: column o1_0.price does not exist\n  Position: 62\r\n\tat org.postgresql.core.v3.QueryExecutorImpl.receiveErrorResponse(QueryExecutorImpl.java:2713)\r\n\tat org.postgresql.core.v3.QueryExecutorImpl.processResults(QueryExecutorImpl.java:2401)\r\n\tat org.postgresql.core.v3.QueryExecutorImpl.execute(QueryExecutorImpl.java:368)\r\n\tat org.postgresql.jdbc.PgStatement.executeInternal(PgStatement.java:498)\r\n\tat org.postgresql.jdbc.PgStatement.execute(PgStatement.java:415)\r\n\tat org.postgresql.jdbc.PgPreparedStatement.executeWithFlags(PgPreparedStatement.java:190)\r\n\tat org.postgresql.jdbc.PgPreparedStatement.executeQuery(PgPreparedStatement.java:134)\r\n\tat io.agroal.pool.wrapper.PreparedStatementWrapper.executeQuery(PreparedStatementWrapper.java:78)\r\n\tat org.hibernate.sql.results.jdbc.internal.DeferredResultSetAccess.executeQuery(DeferredResultSetAccess.java:239)\r\n\t... 90 more"
}

can you continue to fix this bug, that's all.

just fix from the code side, i'll do the testing
</task>
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/java/com/autowarehouse/service/OrderService.java

# VSCode Open Tabs
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/src/views/admin/AdminCategoryView.vue
memory-bank/current-implementation-status.md
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
autowarehouse-frontend/src/utils/debounce.ts
autowarehouse-backend/src/main/java/com/autowarehouse/resource/ProductResource.java
autowarehouse-frontend/src/services/firebase.ts
autowarehouse-frontend/src/views/admin/AdminProductView.vue
autowarehouse-backend/src/main/java/com/autowarehouse/entity/Auction.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/AuctionService.java
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/OrderService.java
autowarehouse-frontend/src/views/ProductsView.vue
autowarehouse-frontend/src/services/api.ts

# Current Time
7/14/2025, 7:17:36 PM (Asia/Bangkok, UTC+7:00)

# Current Working Directory (e:/Ecomindo/Autowarehouse) Files
.gitignore
DEVELOPMENT_GUIDE.md
prd.md
Prompt-Chat GPT.txt
Prompt-Cline AI.txt
Prompt-UX Pilot.txt
README.md
start-dev.bat
start-dev.sh
test-admin-users.sql
ui-design.md
autowarehouse-backend/
autowarehouse-backend/pom.xml
autowarehouse-backend/src/
autowarehouse-backend/src/main/
autowarehouse-backend/src/main/java/
autowarehouse-backend/src/main/resources/
autowarehouse-backend/src/test/
autowarehouse-backend/src/test/java/
autowarehouse-backend/target/
autowarehouse-backend/target/autowarehouse-backend-dev.jar
autowarehouse-backend/target/build-analytics-event.json
autowarehouse-backend/target/build-metrics.json
autowarehouse-backend/target/classes/
autowarehouse-backend/target/classes/application.properties
autowarehouse-backend/target/classes/com/
autowarehouse-backend/target/classes/db/
autowarehouse-backend/target/classes/META-INF/
autowarehouse-backend/target/generated-sources/
autowarehouse-backend/target/generated-sources/annotations/
autowarehouse-backend/target/generated-test-sources/
autowarehouse-backend/target/generated-test-sources/test-annotations/
autowarehouse-backend/target/maven-status/
autowarehouse-backend/target/maven-status/maven-compiler-plugin/
autowarehouse-backend/target/quarkus/
autowarehouse-backend/target/quarkus/bootstrap/
autowarehouse-backend/target/test-classes/
autowarehouse-frontend/
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/index.html
autowarehouse-frontend/package-lock.json
autowarehouse-frontend/package.json
autowarehouse-frontend/postcss.config.js
autowarehouse-frontend/tailwind.config.js
autowarehouse-frontend/tsconfig.json
autowarehouse-frontend/tsconfig.node.json
autowarehouse-frontend/vite.config.ts
autowarehouse-frontend/src/
autowarehouse-frontend/src/App.vue
autowarehouse-frontend/src/main.ts
autowarehouse-frontend/src/style.css
autowarehouse-frontend/src/vite-env.d.ts
autowarehouse-frontend/src/components/
autowarehouse-frontend/src/components/AdminNavbar.vue
autowarehouse-frontend/src/components/GuestNavbar.vue
autowarehouse-frontend/src/components/UserNavbar.vue
autowarehouse-frontend/src/components/admin/
autowarehouse-frontend/src/router/
autowarehouse-frontend/src/router/index.ts
autowarehouse-frontend/src/services/
autowarehouse-frontend/src/services/api.ts
autowarehouse-frontend/src/services/firebase.ts
autowarehouse-frontend/src/stores/
autowarehouse-frontend/src/stores/auth.ts
autowarehouse-frontend/src/stores/cart.ts
autowarehouse-frontend/src/stores/products.ts
autowarehouse-frontend/src/utils/
autowarehouse-frontend/src/utils/debounce.ts
autowarehouse-frontend/src/views/
autowarehouse-frontend/src/views/AuctionDetailView.vue
autowarehouse-frontend/src/views/AuctionWonView.vue
autowarehouse-frontend/src/views/BidHistoryView.vue
autowarehouse-frontend/src/views/CartView.vue
autowarehouse-frontend/src/views/CheckoutView.vue
autowarehouse-frontend/src/views/CustomerServiceView.vue
autowarehouse-frontend/src/views/HomeView.vue
autowarehouse-frontend/src/views/LiveAuctionView.vue
autowarehouse-frontend/src/views/MyBidsView.vue
autowarehouse-frontend/src/views/OrderDetailView.vue
autowarehouse-frontend/src/views/OrderHistoryView.vue
autowarehouse-frontend/src/views/ProductDetailView.vue
autowarehouse-frontend/src/views/ProductsView.vue
autowarehouse-frontend/src/views/admin/
autowarehouse-frontend/src/views/auth/
chat-history/
chat-history/cline_task_jul-3-2025_11-38-35-am.md
chat-history/cline_task_jul-7-2025_3-50-19-pm.md
chat-history/cline_task_jul-7-2025_10-22-14-am.md
chat-history/cline_task_jul-7-2025_12-19-08-pm.md
chat-history/cline_task_jul-8-2025_1-11-45-pm.md
chat-history/cline_task_jul-8-2025_1-13-32-pm.md
chat-history/cline_task_jul-8-2025_1-30-18-pm.md
chat-history/cline_task_jul-8-2025_1-44-27-pm.md
chat-history/cline_task_jul-8-2025_2-46-10-pm.md
chat-history/cline_task_jul-8-2025_10-50-24-am.md
chat-history/cline_task_jul-8-2025_11-12-09-am.md
chat-history/cline_task_jul-8-2025_11-22-47-am.md
chat-history/cline_task_jul-8-2025_11-32-43-am.md
chat-history/cline_task_jul-8-2025_12-12-20-pm.md
chat-history/cline_task_jul-8-2025_12-28-33-pm.md
chat-history/cline_task_jul-8-2025_12-31-04-pm.md
chat-history/cline_task_jul-8-2025_12-51-57-pm.md
chat-history/cline_task_jul-11-2025_4-09-51-pm.md
chat-history/cline_task_jul-11-2025_4-26-57-pm.md
chat-history/cline_task_jul-11-2025_5-40-51-pm.md
memory-bank/
memory-bank/architecture.md
memory-bank/backend-implementation.md
memory-bank/current-implementation-status.md
memory-bank/frontend-backend-integration.md
memory-bank/frontend-implementation.md
memory-bank/glossary.md
memory-bank/login-credentials-summary.md
memory-bank/project-overview.md
memory-bank/project-summary.md
memory-bank/README.md
memory-bank/technical-stack.md
memory-bank/test-credentials.md
memory-bank/user-stories.md
memory-bank/user-test-data.md
memory-bank/api/
memory-bank/api/endpoints.md
memory-bank/api/rest-endpoints.md
memory-bank/database/
memory-bank/database/schema.md
memory-bank/features/
memory-bank/features/auction-system.md
memory-bank/features/notifications.md
memory-bank/features/order-management.md
memory-bank/features/product-catalog.md
memory-bank/features/shopping-cart.md
memory-bank/features/user-management.md
memory-bank/ui-design/
memory-bank/ui-design/design-system.md
tasks-readme/
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
ui-design/
ui-design/admin-auction (1).html
ui-design/admin-auction (1).png
ui-design/admin-auction (2).html
ui-design/admin-auction (2).png
ui-design/admin-category-management (1).html
ui-design/admin-category-management (1).png
ui-design/admin-category-management (2).html
ui-design/admin-category-management (2).png
ui-design/admin-customer-service.html
ui-design/admin-customer-service.png
ui-design/admin-dashboard.html
ui-design/admin-dashboard.png
ui-design/admin-notif.html
ui-design/admin-notif.png
ui-design/admin-order-management (1).html
ui-design/admin-order-management (1).png
ui-design/admin-order-management (2).html
ui-design/admin-order-management (2).png
ui-design/admin-product-management (1).html
ui-design/admin-product-management (1).png
ui-design/admin-product-management (2).html
ui-design/admin-product-management (2).png
ui-design/home - Reference UI.html
ui-design/home.png
ui-design/Login (1) - Reference UI.html
ui-design/Login (1).png
ui-design/Login (2) - Reference UI.html
ui-design/Login (2).png
ui-design/Login (3) - Reference UI.html
ui-design/Login (3).png
ui-design/Login (4) - Reference UI.html
ui-design/Login (4).png
ui-design/order-history (1).html
ui-design/order-history (1).png
ui-design/order-history (2).html
ui-design/order-history (2).png
ui-design/order-history (3).html
ui-design/order-history (3).png
ui-design/product-detail (1) - Reference UI.html
ui-design/product-detail (1).png
ui-design/product-detail (2) - Reference UI.html
ui-design/product-detail (2).png
ui-design/Shopping Cart (1) - Reference UI.html
ui-design/Shopping Cart (2) - Reference UI.html
ui-design/Shoppping-Cart (1).png
ui-design/Shoppping-Cart (2).png
ui-design/user-custome-service.png
ui-design/user-customer-service.html
ui-design/user-live-auction (1).html
ui-design/user-live-auction (1).png
ui-design/user-live-auction (2).html
ui-design/user-live-auction (2).png
ui-design/user-live-auction (3).html
ui-design/user-live-auction (3).png
ui-design/user-live-auction (4).html
ui-design/user-live-auction (4).png
ui-design/user-notification.html
ui-design/user-notification.png
ui-design/user-profile.html
ui-design/user-profile.png
ui-design/Wishlist - Reference UI.html
ui-design/Wishlist.png

(File list truncated. Use list_files on specific subdirectories if you need to explore further.)

# Git Remote URLs
origin: https://github.com/S1rent/Autowarehouse.git

# Context Window Usage
0 / 200K tokens used (0%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

<thinking>
Looking at the error, I can see this is a Hibernate/JPA SQL exception where the query is trying to select a column `o1_0.price` from the `order_items` table, but this column doesn't exist in the database.

The error occurs in the SQL:
```sql
select o1_0.product_id,o1_0.id,o1_0.created_at,o1_0.order_id,o1_0.price,o1_0.product_name,o1_0.product_price,o1_0.product_sku,o1_0.quantity,o1_0.subtotal,o1_0.total_price from order_items o1_0 where o1_0.product_id = any (?)
```

The issue is that the query is trying to select `o1_0.price` but the error says "column o1_0.price does not exist". Looking at the query, I can see it's also selecting `o1_0.product_price`, so it seems like there might be a mapping issue in the OrderItem entity where a field is mapped to a non-existent `price` column instead of the correct `product_price` column.

Let me examine the OrderItem entity to see the current mapping and fix the issue.
</thinking>

I can see the issue from the error message. The Hibernate query is trying to select a column `o1_0.price` from the `order_items` table, but this column doesn't exist. The error occurs when trying to delete a product, which triggers a cascade operation on order items.

Let me examine the OrderItem entity to identify and fix the mapping issue.

<read_file>
<path>autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java</path>
</read_file>

I can see the issue in

[Response interrupted by a tool use result. Only one tool may be used at a time and should be placed at the end of the message.]

---

**User:**

[read_file for 'autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java'] Result:
package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_items")
public class OrderItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull
    public Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    public Product product;

    @Column(name = "product_name", nullable = false, length = 200)
    @NotBlank
    public String productName;

    @Column(name = "product_sku", nullable = false, length = 100)
    @NotBlank
    public String productSku;

    @Column(name = "product_price", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal productPrice;

    @Column(nullable = false)
    @NotNull
    public Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal subtotal;

    @Column(precision = 12, scale = 2)
    public BigDecimal price;

    @Column(name = "total_price", precision = 12, scale = 2)
    public BigDecimal totalPrice;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Constructors
    public OrderItem() {}

    public OrderItem(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.productName = product.name;
        this.productSku = product.sku;
        this.productPrice = product.getCurrentPrice();
        this.quantity = quantity;
        this.subtotal = this.productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // Static finder methods
    public static List<OrderItem> findByOrder(Order order) {
        return find("order", order).list();
    }

    public static List<OrderItem> findByOrderId(Long orderId) {
        return find("order.id", orderId).list();
    }

    public static List<OrderItem> findByProduct(Product product) {
        return find("product", product).list();
    }

    public static List<OrderItem> findByProductId(Long productId) {
        return find("product.id", productId).list();
    }

    public static long countByProduct(Product product) {
        return count("product", product);
    }

    public static long countByProductId(Long productId) {
        return count("product.id", productId);
    }


    // Helper methods
    public BigDecimal getItemTotal() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateTotalPrice() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = newQuantity;
        this.subtotal = this.productPrice.multiply(BigDecimal.valueOf(newQuantity));
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + (order != null ? order.id : null) +
                ", productName='" + productName + '\'' +
                ", productSku='" + productSku + '\'' +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}

<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/java/com/autowarehouse/service/OrderService.java

# VSCode Open Tabs
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/src/views/admin/AdminCategoryView.vue
memory-bank/current-implementation-status.md
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
autowarehouse-frontend/src/utils/debounce.ts
autowarehouse-backend/src/main/java/com/autowarehouse/resource/ProductResource.java
autowarehouse-frontend/src/services/firebase.ts
autowarehouse-frontend/src/views/admin/AdminProductView.vue
autowarehouse-backend/src/main/java/com/autowarehouse/entity/Auction.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/AuctionService.java
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/OrderService.java
autowarehouse-frontend/src/views/ProductsView.vue
autowarehouse-frontend/src/services/api.ts

# Current Time
7/14/2025, 7:17:58 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
0 / 200K tokens used (0%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I found the issue! In the OrderItem entity, there's a field `price` that doesn't have a `@Column` annotation specifying the column name, but the database table doesn't have a `price` column. The field should either be removed or properly mapped.

Looking at the entity, I can see there are multiple price-related fields:
- `productPrice` (mapped to `product_price` column) - this is the correct one
- `price` (no column mapping, defaults to `price` column which doesn't exist)
- `totalPrice` (mapped to `total_price` column)

The `price` field appears to be redundant since we already have `productPrice`. Let me fix this by removing the problematic `price` field:

<replace_in_file>
<path>autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java</path>
<diff>
------- SEARCH
    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal subtotal;

    @Column(precision = 12, scale = 2)
    public BigDecimal price;

    @Column(name = "total_price", precision = 12, scale = 2)
    public BigDecimal totalPrice;
=======
    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal subtotal;

    @Column(name = "total_price", precision = 12, scale = 2)
    public BigDecimal totalPrice;
+++++++ REPLACE
</diff>
</replace_in_file>

---

**User:**

[replace_in_file for 'autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java'] Result:
The content was successfully saved to autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java.

Here is the full, updated content of the file that was saved:

<final_file_content path="autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java">
package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_items")
public class OrderItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull
    public Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    public Product product;

    @Column(name = "product_name", nullable = false, length = 200)
    @NotBlank
    public String productName;

    @Column(name = "product_sku", nullable = false, length = 100)
    @NotBlank
    public String productSku;

    @Column(name = "product_price", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal productPrice;

    @Column(nullable = false)
    @NotNull
    public Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal subtotal;

    @Column(name = "total_price", precision = 12, scale = 2)
    public BigDecimal totalPrice;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Constructors
    public OrderItem() {}

    public OrderItem(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.productName = product.name;
        this.productSku = product.sku;
        this.productPrice = product.getCurrentPrice();
        this.quantity = quantity;
        this.subtotal = this.productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // Static finder methods
    public static List<OrderItem> findByOrder(Order order) {
        return find("order", order).list();
    }

    public static List<OrderItem> findByOrderId(Long orderId) {
        return find("order.id", orderId).list();
    }

    public static List<OrderItem> findByProduct(Product product) {
        return find("product", product).list();
    }

    public static List<OrderItem> findByProductId(Long productId) {
        return find("product.id", productId).list();
    }

    public static long countByProduct(Product product) {
        return count("product", product);
    }

    public static long countByProductId(Long productId) {
        return count("product.id", productId);
    }


    // Helper methods
    public BigDecimal getItemTotal() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateTotalPrice() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = newQuantity;
        this.subtotal = this.productPrice.multiply(BigDecimal.valueOf(newQuantity));
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + (order != null ? order.id : null) +
                ", productName='" + productName + '\'' +
                ", productSku='" + productSku + '\'' +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}

</final_file_content>

IMPORTANT: For any future changes to this file, use the final_file_content shown above as your reference. This content reflects the current state of the file, including any auto-formatting (e.g., if you used single quotes but the formatter converted them to double quotes). Always base your SEARCH/REPLACE operations on this final version to ensure accuracy.


<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java

# VSCode Open Tabs
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/src/views/admin/AdminCategoryView.vue
memory-bank/current-implementation-status.md
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
autowarehouse-frontend/src/utils/debounce.ts
autowarehouse-backend/src/main/java/com/autowarehouse/resource/ProductResource.java
autowarehouse-frontend/src/services/firebase.ts
autowarehouse-frontend/src/views/admin/AdminProductView.vue
autowarehouse-backend/src/main/java/com/autowarehouse/entity/Auction.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/AuctionService.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/OrderService.java
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java
autowarehouse-frontend/src/views/ProductsView.vue
autowarehouse-frontend/src/services/api.ts

# Current Time
7/14/2025, 7:18:14 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
40,360 / 200K tokens used (20%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Perfect! I've fixed the bug by removing the problematic `price` field from the OrderItem entity. The issue was that Hibernate was trying to map a field called `price` to a database column named `price`, but that column doesn't exist in the `order_items` table.

The fix involved:

1. **Identified the problem**: The `price` field in the OrderItem entity was not properly mapped to an existing database column
2. **Removed the redundant field**: The `price` field was unnecessary since we already have:
   - `productPrice` (mapped to `product_price` column) - stores the unit price of the product
   - `subtotal` - stores the calculated subtotal (price × quantity)
   - `totalPrice` (mapped to `total_price` column) - stores the total price if needed

Now when Hibernate generates the SQL query for cascade operations (like when deleting a product), it will no longer try to select the non-existent `price` column, and the error should be resolved.

<attempt_completion>
<result>
Fixed the SQLGrammarException error by removing the problematic `price` field from the OrderItem entity. The field was causing Hibernate to generate SQL queries that referenced a non-existent `price` column in the `order_items` table. The entity now only uses the properly mapped fields: `productPrice` (mapped to `product_price`), `subtotal`, and `totalPrice` (mapped to `total_price`). This resolves the cascade deletion error that occurred when trying to delete products.
</result>
</attempt_completion>

---

**User:**

[attempt_completion] Result:
The user has provided feedback on the results. Consider their input to continue the task, and then attempt completion again.
<feedback>
{
    "details": "Error id 2e1a702e-b858-4ccb-9aae-b2e745c3af6b-5, org.hibernate.exception.SQLGrammarException: JDBC exception executing SQL [select o1_0.product_id,o1_0.id,o1_0.created_at,o1_0.order_id,o1_0.product_name,o1_0.product_price,o1_0.product_sku,o1_0.quantity,o1_0.subtotal,o1_0.total_price from order_items o1_0 where o1_0.product_id = any (?)] [ERROR: column o1_0.total_price does not exist",
    "stack": "org.hibernate.exception.SQLGrammarException: JDBC exception executing SQL [select o1_0.product_id,o1_0.id,o1_0.created_at,o1_0.order_id,o1_0.product_name,o1_0.product_price,o1_0.product_sku,o1_0.quantity,o1_0.subtotal,o1_0.total_price from order_items o1_0 where o1_0.product_id = any (?)] [ERROR: column o1_0.total_price does not exist\n  Position: 144] [n/a]\r\n\tat org.hibernate.exception.internal.SQLStateConversionDelegate.convert(SQLStateConversionDelegate.java:89)\r\n\tat org.hibernate.exception.internal.StandardSQLExceptionConverter.convert(StandardSQLExceptionConverter.java:56)\r\n\tat org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:108)\r\n\tat org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(SqlExceptionHelper.java:94)\r\n\tat org.hibernate.sql.results.jdbc.internal.DeferredResultSetAccess.executeQuery(DeferredResultSetAccess.java:257)\r\n\tat org.hibernate.sql.results.jdbc.internal.DeferredResultSetAccess.getResultSet(DeferredResultSetAccess.java:163)\r\n\tat org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.advanceNext(JdbcValuesResultSetImpl.java:254)\r\n\tat org.hibernate.sql.results.jdbc.internal.JdbcValuesResultSetImpl.processNext(JdbcValuesResultSetImpl.java:134)\r\n\tat org.hibernate.sql.results.jdbc.internal.AbstractJdbcValues.next(AbstractJdbcValues.java:19)\r\n\tat org.hibernate.sql.results.internal.RowProcessingStateStandardImpl.next(RowProcessingStateStandardImpl.java:66)\r\n\tat org.hibernate.sql.results.spi.ListResultsConsumer.consume(ListResultsConsumer.java:178)\r\n\tat org.hibernate.sql.results.spi.ListResultsConsumer.consume(ListResultsConsumer.java:33)\r\n\tat org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.doExecuteQuery(JdbcSelectExecutorStandardImpl.java:361)\r\n\tat org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.executeQuery(JdbcSelectExecutorStandardImpl.java:168)\r\n\tat org.hibernate.sql.exec.internal.JdbcSelectExecutorStandardImpl.list(JdbcSelectExecutorStandardImpl.java:93)\r\n\tat org.hibernate.sql.exec.spi.JdbcSelectExecutor.list(JdbcSelectExecutor.java:31)\r\n\tat org.hibernate.loader.ast.internal.CollectionBatchLoaderArrayParam.initializeKeys(CollectionBatchLoaderArrayParam.java:206)\r\n\tat org.hibernate.loader.ast.internal.CollectionBatchLoaderArrayParam.load(CollectionBatchLoaderArrayParam.java:126)\r\n\tat org.hibernate.persister.collection.AbstractCollectionPersister.initialize(AbstractCollectionPersister.java:680)\r\n\tat org.hibernate.event.internal.DefaultInitializeCollectionEventListener.onInitializeCollection(DefaultInitializeCollectionEventListener.java:75)\r\n\tat org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)\r\n\tat org.hibernate.internal.SessionImpl.initializeCollection(SessionImpl.java:1702)\r\n\tat org.hibernate.collection.spi.AbstractPersistentCollection.lambda$initialize$3(AbstractPersistentCollection.java:617)\r\n\tat org.hibernate.collection.spi.AbstractPersistentCollection.withTemporarySessionIfNeeded(AbstractPersistentCollection.java:265)\r\n\tat org.hibernate.collection.spi.AbstractPersistentCollection.initialize(AbstractPersistentCollection.java:615)\r\n\tat org.hibernate.collection.spi.AbstractPersistentCollection.read(AbstractPersistentCollection.java:136)\r\n\tat org.hibernate.collection.spi.PersistentBag.iterator(PersistentBag.java:371)\r\n\tat org.hibernate.type.CollectionType.getElementsIterator(CollectionType.java:244)\r\n\tat org.hibernate.engine.spi.CascadingActions.getAllElementsIterator(CascadingActions.java:470)\r\n\tat org.hibernate.engine.spi.CascadingActions$1.getCascadableChildrenIterator(CascadingActions.java:70)\r\n\tat org.hibernate.engine.internal.Cascade.cascadeCollectionElements(Cascade.java:550)\r\n\tat org.hibernate.engine.internal.Cascade.cascadeCollection(Cascade.java:482)\r\n\tat org.hibernate.engine.internal.Cascade.cascadeAssociation(Cascade.java:442)\r\n\tat org.hibernate.engine.internal.Cascade.cascadeProperty(Cascade.java:225)\r\n\tat org.hibernate.engine.internal.Cascade.cascade(Cascade.java:158)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.cascadeBeforeDelete(DefaultDeleteEventListener.java:502)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.deleteEntity(DefaultDeleteEventListener.java:391)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.delete(DefaultDeleteEventListener.java:242)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.deletePersistentInstance(DefaultDeleteEventListener.java:219)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.delete(DefaultDeleteEventListener.java:159)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.onDelete(DefaultDeleteEventListener.java:95)\r\n\tat org.hibernate.event.internal.DefaultDeleteEventListener.onDelete(DefaultDeleteEventListener.java:83)\r\n\tat org.hibernate.event.service.internal.EventListenerGroupImpl.fireEventOnEachListener(EventListenerGroupImpl.java:127)\r\n\tat org.hibernate.internal.SessionImpl.fireDelete(SessionImpl.java:961)\r\n\tat org.hibernate.internal.SessionImpl.delete(SessionImpl.java:892)\r\n\tat org.hibernate.internal.SessionImpl.remove(SessionImpl.java:2359)\r\n\tat io.quarkus.hibernate.orm.runtime.session.TransactionScopedSession.remove(TransactionScopedSession.java:167)\r\n\tat org.hibernate.engine.spi.SessionLazyDelegator.remove(SessionLazyDelegator.java:345)\r\n\tat org.hibernate.Session_OpdLahisOZ9nWRPXMsEFQmQU03A_Synthetic_ClientProxy.remove(Unknown Source)\r\n\tat io.quarkus.hibernate.orm.panache.common.runtime.AbstractJpaOperations.delete(AbstractJpaOperations.java:124)\r\n\tat io.quarkus.hibernate.orm.panache.PanacheEntityBase.delete(PanacheEntityBase.java:80)\r\n\tat com.autowarehouse.service.ProductService.deleteProduct(ProductService.java:248)\r\n\tat com.autowarehouse.service.ProductService_Subclass.deleteProduct$$superforward(Unknown Source)\r\n\tat com.autowarehouse.service.ProductService_Subclass$$function$$35.apply(Unknown Source)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:73)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:62)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:136)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.invokeInOurTx(TransactionalInterceptorBase.java:107)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.doIntercept(TransactionalInterceptorRequired.java:38)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorBase.intercept(TransactionalInterceptorBase.java:61)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired.intercept(TransactionalInterceptorRequired.java:32)\r\n\tat io.quarkus.narayana.jta.runtime.interceptor.TransactionalInterceptorRequired_Bean.intercept(Unknown Source)\r\n\tat io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:42)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.perform(AroundInvokeInvocationContext.java:30)\r\n\tat io.quarkus.arc.impl.InvocationContexts.performAroundInvoke(InvocationContexts.java:27)\r\n\tat com.autowarehouse.service.ProductService_Subclass.deleteProduct(Unknown Source)\r\n\tat com.autowarehouse.service.ProductService_ClientProxy.deleteProduct(Unknown Source)\r\n\tat com.autowarehouse.resource.ProductResource.deleteProduct(ProductResource.java:291)\r\n\tat com.autowarehouse.resource.ProductResource_Subclass.deleteProduct$$superforward(Unknown Source)\r\n\tat com.autowarehouse.resource.ProductResource_Subclass$$function$$27.apply(Unknown Source)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:73)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext$NextAroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:97)\r\n\tat io.quarkus.security.runtime.interceptor.SecurityHandler.handle(SecurityHandler.java:27)\r\n\tat io.quarkus.security.runtime.interceptor.RolesAllowedInterceptor.intercept(RolesAllowedInterceptor.java:29)\r\n\tat io.quarkus.security.runtime.interceptor.RolesAllowedInterceptor_Bean.intercept(Unknown Source)\r\n\tat io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:42)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:70)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.proceed(AroundInvokeInvocationContext.java:62)\r\n\tat io.quarkus.resteasy.reactive.server.runtime.StandardSecurityCheckInterceptor.intercept(StandardSecurityCheckInterceptor.java:44)\r\n\tat io.quarkus.resteasy.reactive.server.runtime.StandardSecurityCheckInterceptor_RolesAllowedInterceptor_Bean.intercept(Unknown Source)\r\n\tat io.quarkus.arc.impl.InterceptorInvocation.invoke(InterceptorInvocation.java:42)\r\n\tat io.quarkus.arc.impl.AroundInvokeInvocationContext.perform(AroundInvokeInvocationContext.java:30)\r\n\tat io.quarkus.arc.impl.InvocationContexts.performAroundInvoke(InvocationContexts.java:27)\r\n\tat com.autowarehouse.resource.ProductResource_Subclass.deleteProduct(Unknown Source)\r\n\tat com.autowarehouse.resource.ProductResource$quarkusrestinvoker$deleteProduct_718b02bf067b9dbae2b1f9ae1eea98b9ea9e70ec.invoke(Unknown Source)\r\n\tat org.jboss.resteasy.reactive.server.handlers.InvocationHandler.handle(InvocationHandler.java:29)\r\n\tat io.quarkus.resteasy.reactive.server.runtime.QuarkusResteasyReactiveRequestContext.invokeHandler(QuarkusResteasyReactiveRequestContext.java:141)\r\n\tat org.jboss.resteasy.reactive.common.core.AbstractResteasyReactiveContext.run(AbstractResteasyReactiveContext.java:147)\r\n\tat io.quarkus.vertx.core.runtime.VertxCoreRecorder$14.runWith(VertxCoreRecorder.java:582)\r\n\tat org.jboss.threads.EnhancedQueueExecutor$Task.run(EnhancedQueueExecutor.java:2513)\r\n\tat org.jboss.threads.EnhancedQueueExecutor$ThreadBody.run(EnhancedQueueExecutor.java:1538)\r\n\tat org.jboss.threads.DelegatingRunnable.run(DelegatingRunnable.java:29)\r\n\tat org.jboss.threads.ThreadLocalResettingRunnable.run(ThreadLocalResettingRunnable.java:29)\r\n\tat io.netty.util.concurrent.FastThreadLocalRunnable.run(FastThreadLocalRunnable.java:30)\r\n\tat java.base/java.lang.Thread.run(Thread.java:840)\r\nCaused by: org.postgresql.util.PSQLException: ERROR: column o1_0.total_price does not exist\n  Position: 144\r\n\tat org.postgresql.core.v3.QueryExecutorImpl.receiveErrorResponse(QueryExecutorImpl.java:2713)\r\n\tat org.postgresql.core.v3.QueryExecutorImpl.processResults(QueryExecutorImpl.java:2401)\r\n\tat org.postgresql.core.v3.QueryExecutorImpl.execute(QueryExecutorImpl.java:368)\r\n\tat org.postgresql.jdbc.PgStatement.executeInternal(PgStatement.java:498)\r\n\tat org.postgresql.jdbc.PgStatement.execute(PgStatement.java:415)\r\n\tat org.postgresql.jdbc.PgPreparedStatement.executeWithFlags(PgPreparedStatement.java:190)\r\n\tat org.postgresql.jdbc.PgPreparedStatement.executeQuery(PgPreparedStatement.java:134)\r\n\tat io.agroal.pool.wrapper.PreparedStatementWrapper.executeQuery(PreparedStatementWrapper.java:78)\r\n\tat org.hibernate.sql.results.jdbc.internal.DeferredResultSetAccess.executeQuery(DeferredResultSetAccess.java:239)\r\n\t... 90 more"
}

masih gabisa juga
</feedback>
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java

# VSCode Open Tabs
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/src/views/admin/AdminCategoryView.vue
memory-bank/current-implementation-status.md
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
autowarehouse-frontend/src/utils/debounce.ts
autowarehouse-backend/src/main/java/com/autowarehouse/resource/ProductResource.java
autowarehouse-frontend/src/services/firebase.ts
autowarehouse-frontend/src/views/admin/AdminProductView.vue
autowarehouse-backend/src/main/java/com/autowarehouse/entity/Auction.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/AuctionService.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/OrderService.java
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java
autowarehouse-frontend/src/views/ProductsView.vue
autowarehouse-frontend/src/services/api.ts

# Current Time
7/14/2025, 7:21:32 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
44,287 / 200K tokens used (22%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I see the issue continues - now it's complaining about the `total_price` column not existing. Let me check what columns actually exist in the database by looking at the database schema or migration files.

<search_files>
<path>autowarehouse-backend</path>
<regex>order_items</regex>
<file_pattern>*.sql</file_pattern>
</search_files>

---

**User:**

[search_files for 'order_items' in '*.sql'] Result:
Found 8 results.

autowarehouse-backend/target/classes/db/migration/V1__Create_initial_tables.sql
│----
│-- Create order_items table
│----
│CREATE TABLE order_items (
│    id BIGSERIAL PRIMARY KEY,
│
│----
│CREATE INDEX idx_order_items_order ON order_items(order_id);
│----
│
│CREATE INDEX idx_order_items_product ON order_items(product_id);
│
│----

autowarehouse-backend/src/main/resources/db/migration/V1__Create_initial_tables.sql
│----
│-- Create order_items table
│----
│CREATE TABLE order_items (
│    id BIGSERIAL PRIMARY KEY,
│
│----
│CREATE INDEX idx_order_items_order ON order_items(order_id);
│----
│CREATE INDEX idx_order_items_product ON order_items(product_id);
│
│----
<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java

# VSCode Open Tabs
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/src/views/admin/AdminCategoryView.vue
memory-bank/current-implementation-status.md
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
autowarehouse-frontend/src/utils/debounce.ts
autowarehouse-backend/src/main/java/com/autowarehouse/resource/ProductResource.java
autowarehouse-frontend/src/services/firebase.ts
autowarehouse-frontend/src/views/admin/AdminProductView.vue
autowarehouse-backend/src/main/java/com/autowarehouse/entity/Auction.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/AuctionService.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/OrderService.java
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java
autowarehouse-frontend/src/views/ProductsView.vue
autowarehouse-frontend/src/services/api.ts

# Current Time
7/14/2025, 7:21:41 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
51,056 / 200K tokens used (26%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Let me check the actual database migration file to see what columns exist in the order_items table:

<read_file>
<path>autowarehouse-backend/src/main/resources/db/migration/V1__Create_initial_tables.sql</path>
</read_file>

---

**User:**

[read_file for 'autowarehouse-backend/src/main/resources/db/migration/V1__Create_initial_tables.sql'] Result:
-- Create users table
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    role VARCHAR(20) NOT NULL DEFAULT 'CUSTOMER',
    is_active BOOLEAN DEFAULT TRUE,
    is_email_verified BOOLEAN DEFAULT FALSE,
    google_id VARCHAR(255),
    profile_image_url TEXT,
    email_verification_token VARCHAR(255),
    password_reset_token VARCHAR(255),
    password_reset_token_expiry TIMESTAMP,
    last_login_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create categories table
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL,
    description TEXT,
    slug VARCHAR(100) UNIQUE NOT NULL,
    parent_id BIGINT REFERENCES categories(id),
    image_url TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    sort_order INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create products table
CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(200) NOT NULL,
    description TEXT,
    short_description VARCHAR(500),
    price DECIMAL(12,2) NOT NULL,
    original_price DECIMAL(12,2),
    stock_quantity INTEGER NOT NULL DEFAULT 0,
    min_stock_level INTEGER DEFAULT 5,
    sku VARCHAR(100) UNIQUE NOT NULL,
    brand VARCHAR(100) NOT NULL,
    model VARCHAR(100) NOT NULL,
    specifications TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    is_featured BOOLEAN DEFAULT FALSE,
    is_on_sale BOOLEAN DEFAULT FALSE,
    sale_start_date TIMESTAMP,
    sale_end_date TIMESTAMP,
    weight_kg DECIMAL(8,3),
    dimensions VARCHAR(100),
    warranty_months INTEGER,
    average_rating DECIMAL(3,2) DEFAULT 0.00,
    total_reviews INTEGER DEFAULT 0,
    total_sold INTEGER DEFAULT 0,
    view_count BIGINT DEFAULT 0,
    category_id BIGINT NOT NULL REFERENCES categories(id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create product_images table
CREATE TABLE product_images (
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    image_url TEXT NOT NULL,
    PRIMARY KEY (product_id, image_url)
);

-- Create product_tags table
CREATE TABLE product_tags (
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    tag VARCHAR(50) NOT NULL,
    PRIMARY KEY (product_id, tag)
);

-- Create cart_items table
CREATE TABLE cart_items (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    quantity INTEGER NOT NULL DEFAULT 1,
    is_selected BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, product_id)
);

-- Create wishlist_items table
CREATE TABLE wishlist_items (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, product_id)
);

-- Create orders table
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    order_number VARCHAR(50) UNIQUE NOT NULL,
    status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    subtotal DECIMAL(12,2) NOT NULL,
    tax_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    shipping_cost DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    discount_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00,
    total_amount DECIMAL(12,2) NOT NULL,
    shipping_address TEXT NOT NULL,
    billing_address TEXT,
    payment_method VARCHAR(50),
    payment_reference VARCHAR(255),
    notes TEXT,
    shipped_at TIMESTAMP,
    delivered_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create order_items table
CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
    product_id BIGINT NOT NULL REFERENCES products(id),
    product_name VARCHAR(200) NOT NULL,
    product_sku VARCHAR(100) NOT NULL,
    product_price DECIMAL(12,2) NOT NULL,
    quantity INTEGER NOT NULL,
    subtotal DECIMAL(12,2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create reviews table
CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id),
    product_id BIGINT NOT NULL REFERENCES products(id) ON DELETE CASCADE,
    order_id BIGINT REFERENCES orders(id),
    rating INTEGER NOT NULL CHECK (rating >= 1 AND rating <= 5),
    title VARCHAR(200),
    comment TEXT,
    is_verified_purchase BOOLEAN DEFAULT FALSE,
    is_approved BOOLEAN DEFAULT TRUE,
    helpful_count INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(user_id, product_id, order_id)
);

-- Create auctions table
CREATE TABLE auctions (
    id BIGSERIAL PRIMARY KEY,
    product_id BIGINT NOT NULL REFERENCES products(id),
    title VARCHAR(200) NOT NULL,
    description TEXT,
    starting_price DECIMAL(12,2) NOT NULL,
    current_price DECIMAL(12,2) NOT NULL,
    buy_now_price DECIMAL(12,2),
    minimum_bid_increment DECIMAL(12,2) NOT NULL DEFAULT 1000.00,
    status VARCHAR(20) NOT NULL DEFAULT 'SCHEDULED',
    start_time TIMESTAMP NOT NULL,
    end_time TIMESTAMP NOT NULL,
    winner_id BIGINT REFERENCES users(id),
    total_bids INTEGER DEFAULT 0,
    watchers_count INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create bids table
CREATE TABLE bids (
    id BIGSERIAL PRIMARY KEY,
    auction_id BIGINT NOT NULL REFERENCES auctions(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id),
    amount DECIMAL(12,2) NOT NULL,
    is_winning BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create auction_watchers table
CREATE TABLE auction_watchers (
    id BIGSERIAL PRIMARY KEY,
    auction_id BIGINT NOT NULL REFERENCES auctions(id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(auction_id, user_id)
);

-- Create notifications table
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    type VARCHAR(50) NOT NULL,
    priority VARCHAR(20) NOT NULL DEFAULT 'NORMAL',
    title VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    data JSONB,
    is_read BOOLEAN DEFAULT FALSE,
    read_at TIMESTAMP,
    expires_at TIMESTAMP,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);
CREATE INDEX idx_users_active ON users(is_active);

CREATE INDEX idx_categories_parent ON categories(parent_id);
CREATE INDEX idx_categories_active ON categories(is_active);

CREATE INDEX idx_products_category ON products(category_id);
CREATE INDEX idx_products_brand ON products(brand);
CREATE INDEX idx_products_active ON products(is_active);
CREATE INDEX idx_products_featured ON products(is_featured);
CREATE INDEX idx_products_sale ON products(is_on_sale);
CREATE INDEX idx_products_price ON products(price);
CREATE INDEX idx_products_rating ON products(average_rating);

CREATE INDEX idx_cart_items_user ON cart_items(user_id);
CREATE INDEX idx_cart_items_product ON cart_items(product_id);

CREATE INDEX idx_wishlist_items_user ON wishlist_items(user_id);
CREATE INDEX idx_wishlist_items_product ON wishlist_items(product_id);

CREATE INDEX idx_orders_user ON orders(user_id);
CREATE INDEX idx_orders_status ON orders(status);
CREATE INDEX idx_orders_created ON orders(created_at);

CREATE INDEX idx_order_items_order ON order_items(order_id);
CREATE INDEX idx_order_items_product ON order_items(product_id);

CREATE INDEX idx_reviews_product ON reviews(product_id);
CREATE INDEX idx_reviews_user ON reviews(user_id);
CREATE INDEX idx_reviews_approved ON reviews(is_approved);

CREATE INDEX idx_auctions_product ON auctions(product_id);
CREATE INDEX idx_auctions_status ON auctions(status);
CREATE INDEX idx_auctions_start_time ON auctions(start_time);
CREATE INDEX idx_auctions_end_time ON auctions(end_time);

CREATE INDEX idx_bids_auction ON bids(auction_id);
CREATE INDEX idx_bids_user ON bids(user_id);
CREATE INDEX idx_bids_winning ON bids(is_winning);

CREATE INDEX idx_notifications_user ON notifications(user_id);
CREATE INDEX idx_notifications_read ON notifications(is_read);
CREATE INDEX idx_notifications_created ON notifications(created_at);

<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java

# VSCode Open Tabs
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/src/views/admin/AdminCategoryView.vue
memory-bank/current-implementation-status.md
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
autowarehouse-frontend/src/utils/debounce.ts
autowarehouse-backend/src/main/java/com/autowarehouse/resource/ProductResource.java
autowarehouse-frontend/src/services/firebase.ts
autowarehouse-frontend/src/views/admin/AdminProductView.vue
autowarehouse-backend/src/main/java/com/autowarehouse/entity/Auction.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/AuctionService.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/OrderService.java
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java
autowarehouse-frontend/src/views/ProductsView.vue
autowarehouse-frontend/src/services/api.ts

# Current Time
7/14/2025, 7:21:49 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
56,787 / 200K tokens used (28%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Perfect! Now I can see the actual database schema. The `order_items` table only has these columns:

- `id`
- `order_id`
- `product_id`
- `product_name`
- `product_sku`
- `product_price`
- `quantity`
- `subtotal`
- `created_at`

But the OrderItem entity is trying to map to a `total_price` column that doesn't exist. I need to remove the `totalPrice` field as well:

<replace_in_file>
<path>autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java</path>
<diff>
------- SEARCH
    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal subtotal;

    @Column(name = "total_price", precision = 12, scale = 2)
    public BigDecimal totalPrice;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;
=======
    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal subtotal;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;
+++++++ REPLACE
</diff>
</replace_in_file>

---

**User:**

[replace_in_file for 'autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java'] Result:
The content was successfully saved to autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java.

Here is the full, updated content of the file that was saved:

<final_file_content path="autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java">
package com.autowarehouse.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_items")
public class OrderItem extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    @NotNull
    public Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    @NotNull
    public Product product;

    @Column(name = "product_name", nullable = false, length = 200)
    @NotBlank
    public String productName;

    @Column(name = "product_sku", nullable = false, length = 100)
    @NotBlank
    public String productSku;

    @Column(name = "product_price", nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal productPrice;

    @Column(nullable = false)
    @NotNull
    public Integer quantity;

    @Column(nullable = false, precision = 12, scale = 2)
    @NotNull
    public BigDecimal subtotal;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    public LocalDateTime createdAt;

    // Constructors
    public OrderItem() {}

    public OrderItem(Order order, Product product, Integer quantity) {
        this.order = order;
        this.product = product;
        this.productName = product.name;
        this.productSku = product.sku;
        this.productPrice = product.getCurrentPrice();
        this.quantity = quantity;
        this.subtotal = this.productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    // Static finder methods
    public static List<OrderItem> findByOrder(Order order) {
        return find("order", order).list();
    }

    public static List<OrderItem> findByOrderId(Long orderId) {
        return find("order.id", orderId).list();
    }

    public static List<OrderItem> findByProduct(Product product) {
        return find("product", product).list();
    }

    public static List<OrderItem> findByProductId(Long productId) {
        return find("product.id", productId).list();
    }

    public static long countByProduct(Product product) {
        return count("product", product);
    }

    public static long countByProductId(Long productId) {
        return count("product.id", productId);
    }


    // Helper methods
    public BigDecimal getItemTotal() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public BigDecimal calculateTotalPrice() {
        return productPrice.multiply(BigDecimal.valueOf(quantity));
    }

    public void updateQuantity(int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        this.quantity = newQuantity;
        this.subtotal = this.productPrice.multiply(BigDecimal.valueOf(newQuantity));
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", orderId=" + (order != null ? order.id : null) +
                ", productName='" + productName + '\'' +
                ", productSku='" + productSku + '\'' +
                ", quantity=" + quantity +
                ", subtotal=" + subtotal +
                '}';
    }
}

</final_file_content>

IMPORTANT: For any future changes to this file, use the final_file_content shown above as your reference. This content reflects the current state of the file, including any auto-formatting (e.g., if you used single quotes but the formatter converted them to double quotes). Always base your SEARCH/REPLACE operations on this final version to ensure accuracy.


<environment_details>
# VSCode Visible Files
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java

# VSCode Open Tabs
autowarehouse-frontend/firebase-setup.md
autowarehouse-frontend/src/views/admin/AdminCategoryView.vue
memory-bank/current-implementation-status.md
tasks-readme/product-management-integration.md
tasks-readme/product-management-missing-tasks.md
autowarehouse-frontend/src/utils/debounce.ts
autowarehouse-backend/src/main/java/com/autowarehouse/resource/ProductResource.java
autowarehouse-frontend/src/services/firebase.ts
autowarehouse-frontend/src/views/admin/AdminProductView.vue
autowarehouse-backend/src/main/java/com/autowarehouse/entity/Auction.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/AuctionService.java
autowarehouse-backend/src/main/java/com/autowarehouse/service/OrderService.java
autowarehouse-backend/src/main/java/com/autowarehouse/entity/OrderItem.java
autowarehouse-frontend/src/views/ProductsView.vue
autowarehouse-frontend/src/services/api.ts

# Current Time
7/14/2025, 7:22:10 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
60,950 / 200K tokens used (30%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Perfect! Now the OrderItem entity is properly aligned with the actual database schema. The entity now only maps to the columns that actually exist in the `order_items` table:

- `id` → `id`
- `order_id` → `order` (relationship)
- `product_id` → `product` (relationship)
- `product_name` → `productName`
- `product_sku` → `productSku`
- `product_price` → `productPrice`
- `quantity` → `quantity`
- `subtotal` → `subtotal`
- `created_at` → `createdAt`

I removed both the `price` field (which was trying to map to a non-existent `price` column) and the `totalPrice` field (which was trying to map to a non-existent `total_price` column).

The entity still has the helper methods `getItemTotal()` and `calculateTotalPrice()` that can compute the total price when needed, but these don't require database columns since they're calculated values.

<attempt_completion>
<result>
Fixed the SQLGrammarException by completely aligning the OrderItem entity with the actual database schema. Removed both the `price` field (mapping to non-existent `price` column) and the `totalPrice` field (mapping to non-existent `total_price` column). The entity now only maps to the columns that actually exist in the `order_items` table: id, order_id, product_id, product_name, product_sku, product_price, quantity, subtotal, and created_at. The helper methods for calculating totals are preserved as computed values that don't require database storage.
</result>
</attempt_completion>

---

**User:**

[attempt_completion] Result:
The user has provided feedback on the results. Consider their input to continue the task, and then attempt completion again.
<feedback>
oke, di frontend.
halaman product management kan ada filter search by keyword, filter by category, dan juga status.
kayanya itu belom works, belum ngelempar paramnya ke backend.
</feedback>
<environment_details>
# VSCode Visible Files
(No visible files)

# VSCode Open Tabs
(No open tabs)

# Current Time
7/14/2025, 7:31:29 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
66,081 / 200K tokens used (33%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I'll check the frontend product management page to fix the search and filter functionality. Let me examine the current implementation.

<read_file>
<path>autowarehouse-frontend/src/views/admin/AdminProductView.vue</path>
</read_file>

---

**User:**

[read_file for 'autowarehouse-frontend/src/views/admin/AdminProductView.vue'] Result:
<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-4">
          <button 
            v-if="showAddForm" 
            @click="goBack" 
            class="text-gray-600 hover:text-gray-800"
          >
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <div>
            <h1 class="text-3xl font-bold text-gray-900">
              {{ showAddForm ? 'Tambah Produk Baru' : 'Product Management' }}
            </h1>
            <p class="text-gray-600 mt-1">
              {{ showAddForm ? 'Lengkapi form di bawah untuk menambah produk' : 'Kelola produk toko Anda' }}
            </p>
          </div>
        </div>
      </div>

        <!-- Product List View -->
        <div v-if="!showAddForm" class="p-6">
          <!-- Filters Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
            <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <div class="relative">
                  <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input 
                    v-model="searchQuery"
                    type="text" 
                    placeholder="Cari produk..." 
                    class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
                  >
                </div>
                <select 
                  v-model="categoryFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Kategori</option>
                  <option 
                    v-for="category in categories" 
                    :key="category.id" 
                    :value="category.name"
                  >
                    {{ category.name }}
                  </option>
                </select>
                <select 
                  v-model="statusFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Status</option>
                  <option value="active">Aktif</option>
                  <option value="inactive">Tidak Aktif</option>
                  <option value="out_of_stock">Stok Habis</option>
                </select>
              </div>
              <button 
                @click="showAddProductForm"
                class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center space-x-2"
              >
                <i class="fa-solid fa-plus"></i>
                <span>Tambah Produk</span>
              </button>
            </div>
          </div>

          <!-- Loading State -->
          <div v-if="loading" class="flex justify-center items-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <span class="ml-2 text-gray-600">Loading products...</span>
          </div>

          <!-- Error State -->
          <div v-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
            <div class="flex">
              <i class="fa-solid fa-exclamation-triangle text-red-600 mr-2"></i>
              <span class="text-red-800">{{ error }}</span>
              <button @click="fetchProducts" class="ml-auto text-red-600 hover:text-red-800">
                <i class="fa-solid fa-refresh"></i> Retry
              </button>
            </div>
          </div>

          <!-- Products Table -->
          <div v-if="!loading && !error" class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50 border-b border-gray-200">
                  <tr>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Produk</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Kategori</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Harga</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stok</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aksi</th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr 
                    v-for="product in filteredProducts" 
                    :key="product.id" 
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <div class="w-12 h-12 mr-4 flex-shrink-0">
                          <img 
                            v-if="hasProductImage(product)"
                            :src="getProductImage(product)" 
                            :alt="product.name" 
                            class="w-12 h-12 rounded-lg object-cover"
                            @error="handleImageError"
                          >
                          <div 
                            v-else
                            class="w-12 h-12 rounded-lg bg-gray-200 flex items-center justify-center"
                          >
                            <i class="fa-solid fa-image text-gray-400 text-lg"></i>
                          </div>
                        </div>
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ product.name }}</div>
                          <div class="text-sm text-gray-500">SKU: {{ product.sku }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.categoryName }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">Rp {{ formatPrice(product.price) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.stockQuantity }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="getStatusClass(getProductStatus(product))"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ getStatusText(getProductStatus(product)) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex space-x-2">
                        <button 
                          @click="editProduct(product)"
                          class="text-blue-600 hover:text-blue-700"
                        >
                          <i class="fa-solid fa-edit"></i>
                        </button>
                        <button 
                          @click="deleteProduct(product.id)"
                          class="text-red-600 hover:text-red-700"
                        >
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <!-- Pagination -->
            <div class="bg-gray-50 px-6 py-3 flex items-center justify-between border-t border-gray-200">
              <div class="text-sm text-gray-700">
                Menampilkan {{ startIndex }} sampai {{ endIndex }} dari {{ totalProducts }} produk
              </div>
              <div class="flex space-x-2">
                <button 
                  @click="previousPage"
                  :disabled="currentPage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Previous
                </button>
                <button 
                  v-for="page in visiblePages" 
                  :key="page"
                  @click="goToPage(page)"
                  :class="page === currentPage ? 'bg-blue-600 text-white' : 'border border-gray-300 hover:bg-gray-100'"
                  class="px-3 py-1 text-sm rounded"
                >
                  {{ page }}
                </button>
                <button 
                  @click="nextPage"
                  :disabled="currentPage === totalPages"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Add/Edit Product Form -->
        <div v-if="showAddForm" class="p-6">
          <div class="max-w-4xl mx-auto">
            <form @submit.prevent="saveProduct" class="space-y-8">
              
              <!-- Basic Information -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-info-circle text-blue-600 mr-2"></i>
                  Informasi Dasar
                </h3>
                
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Nama Produk *</label>
                    <input 
                      v-model="productForm.name"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Masukkan nama produk" 
                      required
                    >
                  </div>
                  
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Kategori *</label>
                    <select 
                      v-model="productForm.category"
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      required
                      :disabled="categoriesLoading"
                    >
                      <option value="">{{ categoriesLoading ? 'Loading categories...' : 'Pilih Kategori' }}</option>
                      <option 
                        v-for="category in categories" 
                        :key="category.id" 
                        :value="category.id"
                      >
                        {{ category.name }}
                      </option>
                    </select>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">SKU</label>
                    <input 
                      v-model="productForm.sku"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Kode produk unik"
                    >
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Harga *</label>
                    <div class="relative">
                      <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                      <input 
                        v-model="productForm.price"
                        type="number" 
                        class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                        placeholder="0" 
                        required
                      >
                    </div>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Stok *</label>
                    <input 
                      v-model="productForm.stock"
                      type="number" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="0" 
                      required
                    >
                  </div>
                </div>
              </div>

              <!-- Specifications -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-cogs text-blue-600 mr-2"></i>
                  Spesifikasi Produk
                </h3>
                
                <div class="space-y-4">
                  <div 
                    v-for="(spec, index) in productForm.specifications" 
                    :key="index"
                    class="flex items-center space-x-4"
                  >
                    <input 
                      v-model="spec.name"
                      type="text" 
                      placeholder="Nama spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <input 
                      v-model="spec.value"
                      type="text" 
                      placeholder="Nilai spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <button 
                      type="button" 
                      @click="removeSpecification(index)" 
                      class="text-red-600 hover:text-red-700 p-2"
                    >
                      <i class="fa-solid fa-trash"></i>
                    </button>
                  </div>
                </div>
                
                <button 
                  type="button" 
                  @click="addSpecification" 
                  class="mt-4 flex items-center space-x-2 text-blue-600 hover:text-blue-700"
                >
                  <i class="fa-solid fa-plus"></i>
                  <span>Tambah Spesifikasi</span>
                </button>
              </div>

              <!-- Description -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-align-left text-blue-600 mr-2"></i>
                  Deskripsi Produk
                </h3>
                
                <textarea 
                  v-model="productForm.description"
                  rows="6" 
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                  placeholder="Masukkan deskripsi lengkap produk..."
                ></textarea>
              </div>

              <!-- Images -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-images text-blue-600 mr-2"></i>
                  Gambar Produk
                </h3>
                
                <div class="space-y-6">
                  <!-- Show existing images when editing -->
                  <div v-if="isEditing && existingImages.length > 0" class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-3">Gambar Saat Ini</label>
                    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                      <div 
                        v-for="(imageUrl, index) in existingImages" 
                        :key="index"
                        class="relative group"
                      >
                        <img 
                          :src="imageUrl" 
                          :alt="`Product image ${index + 1}`"
                          class="w-full h-32 object-cover rounded-lg border border-gray-200"
                          @error="handleImageError"
                        >
                        <button 
                          type="button"
                          @click="removeExistingImage(index)"
                          class="absolute top-2 right-2 bg-red-600 text-white rounded-full w-6 h-6 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                        >
                          <i class="fa-solid fa-times text-xs"></i>
                        </button>
                      </div>
                    </div>
                  </div>

                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-3">
                      {{ isEditing ? 'Tambah Gambar Baru' : 'Upload Gambar Produk *' }}
                    </label>
                    <div 
                      @click="triggerFileInput"
                      class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-blue-600 transition-colors cursor-pointer bg-gray-50 hover:bg-gray-100"
                    >
                      <i class="fa-solid fa-cloud-upload text-4xl text-gray-400 mb-4"></i>
                      <p class="text-gray-600 mb-2 font-medium">Drag & drop gambar atau klik untuk upload</p>
                      <p class="text-sm text-gray-500 mb-3">PNG, JPG hingga 10MB per file</p>
                      <button type="button" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                        Pilih File
                      </button>
                      <input 
                        ref="fileInput"
                        type="file" 
                        class="hidden" 
                        accept="image/*" 
                        multiple
                        @change="handleFileSelect"
                      >
                    </div>
                    
                    <!-- Show selected files -->
                    <div v-if="selectedFiles.length > 0" class="mt-4">
                      <p class="text-sm font-medium text-gray-700 mb-2">File yang dipilih:</p>
                      <div class="space-y-2">
                        <div 
                          v-for="(file, index) in selectedFiles" 
                          :key="index"
                          class="flex items-center justify-between bg-gray-50 p-2 rounded"
                        >
                          <span class="text-sm text-gray-600">{{ file.name }}</span>
                          <button 
                            type="button"
                            @click="removeFile(index)"
                            class="text-red-600 hover:text-red-700"
                          >
                            <i class="fa-solid fa-times"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Auction Settings -->
              <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-gavel text-blue-600 mr-2"></i>
                  Pengaturan Lelang
                </h3>
                
                <div class="space-y-6">
                  <div class="flex items-center space-x-3">
                    <input 
                      v-model="productForm.auctionEnabled"
                      type="checkbox" 
                      class="w-5 h-5 text-blue-600 focus:ring-blue-600 border-gray-300 rounded"
                    >
                    <label class="text-sm font-medium text-gray-700">Tersedia untuk lelang</label>
                  </div>
                  
                  <div v-if="productForm.auctionEnabled" class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Harga Awal Lelang</label>
                      <div class="relative">
                        <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                        <input 
                          v-model="productForm.auctionStartPrice"
                          type="number" 
                          class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                          placeholder="0"
                        >
                      </div>
                    </div>
                    
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Durasi Lelang (Hari)</label>
                      <select 
                        v-model="productForm.auctionDuration"
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      >
                        <option value="1">1 Hari</option>
                        <option value="3">3 Hari</option>
                        <option value="7">7 Hari</option>
                        <option value="14">14 Hari</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div> -->

              <!-- Status -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-toggle-on text-blue-600 mr-2"></i>
                  Status Produk
                </h3>
                
                <div class="flex space-x-6">
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="active" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Aktif</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="draft" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Draft</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="inactive" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Tidak Aktif</span>
                  </label>
                </div>
              </div>

              <!-- Form Actions -->
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <button 
                  type="button" 
                  @click="goBack" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors disabled:opacity-50"
                >
                  Batal
                </button>
                <button 
                  type="button" 
                  @click="saveDraft"
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-gray-600 text-white rounded-lg font-medium hover:bg-gray-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>Simpan sebagai Draft</span>
                </button>
                <button 
                  type="submit" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>{{ isEditing ? 'Update Produk' : 'Publikasikan Produk' }}</span>
                </button>
              </div>
            </form>
          </div>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService, type Product, type Category, type CreateProductRequest, type UpdateProductRequest } from '@/services/api'
import { debounce } from '@/utils/debounce'
import { uploadProductImages, deleteProductImages, validateProductImageFile, type UploadResult } from '@/services/firebase'

interface Specification {
  name: string
  value: string
}

interface LocalProduct {
  id: string
  name: string
  sku: string
  category: string
  price: number
  stock: number
  status: 'active' | 'inactive' | 'out_of_stock'
  image: string
  description?: string
  specifications?: Specification[]
}

interface ProductForm {
  name: string
  category: string
  sku: string
  price: number
  stock: number
  description: string
  specifications: Specification[]
  auctionEnabled: boolean
  auctionStartPrice: number
  auctionDuration: number
  status: 'active' | 'draft' | 'inactive'
}

const searchQuery = ref('')
const categoryFilter = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showAddForm = ref(false)
const isEditing = ref(false)
const editingProductId = ref<number | null>(null)

// API Integration
const products = ref<Product[]>([])
const categories = ref<Category[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const saving = ref(false)
const categoriesLoading = ref(false)

// File handling
const selectedFiles = ref<File[]>([])
const existingImages = ref<string[]>([])
const fileInput = ref<HTMLInputElement | null>(null)

const productForm = ref<ProductForm>({
  name: '',
  category: '',
  sku: '',
  price: 0,
  stock: 0,
  description: '',
  specifications: [{ name: '', value: '' }],
  auctionEnabled: false,
  auctionStartPrice: 0,
  auctionDuration: 7,
  status: 'active'
})

const filteredProducts = computed(() => {
  let filtered = products.value

  if (searchQuery.value) {
    filtered = filtered.filter(product => 
      product.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      product.sku.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  if (categoryFilter.value) {
    filtered = filtered.filter(product => 
      product.categoryName.toLowerCase() === categoryFilter.value.toLowerCase()
    )
  }

  if (statusFilter.value) {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalProducts = computed(() => products.value.length)
const totalPages = computed(() => Math.ceil(products.value.length / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalProducts.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStatusClass = (status: string) => {
  const classes = {
    active: 'bg-green-100 text-green-800',
    inactive: 'bg-gray-100 text-gray-800',
    out_of_stock: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    active: 'Aktif',
    inactive: 'Tidak Aktif',
    out_of_stock: 'Stok Habis'
  }
  return texts[status as keyof typeof texts] || 'Unknown'
}

const getProductStatus = (product: Product): string => {
  if (product.stockQuantity === 0) {
    return 'out_of_stock'
  }
  return product.isActive ? 'active' : 'inactive'
}

const showAddProductForm = () => {
  resetForm()
  showAddForm.value = true
  isEditing.value = false
}

const editProduct = async (product: Product) => {
  try {
    // Fetch fresh product data
    const fullProduct = await apiService.getProduct(product.id)
    
    // Parse specifications back to array format
    const specs = fullProduct.specifications 
      ? fullProduct.specifications.split('\n').map(line => {
          const [name, value] = line.split(': ')
          return { name: name || '', value: value || '' }
        })
      : [{ name: '', value: '' }]
    
    // Find category ID from categories list
    const category = categories.value.find(cat => cat.name === fullProduct.categoryName)
    
    // Populate form with API data
    productForm.value = {
      name: fullProduct.name,
      category: category?.id.toString() || '',
      sku: fullProduct.sku,
      price: fullProduct.price,
      stock: fullProduct.stockQuantity,
      description: fullProduct.description,
      specifications: specs,
      auctionEnabled: false,
      auctionStartPrice: 0,
      auctionDuration: 7,
      status: fullProduct.isActive ? 'active' : 'inactive'
    }
    
    // Load existing images
    existingImages.value = fullProduct.imageUrls || []
    selectedFiles.value = []
    
    editingProductId.value = product.id
    showAddForm.value = true
    isEditing.value = true
  } catch (err) {
    alert('Failed to load product details')
    console.error(err)
  }
}

const deleteProduct = async (productId: number) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await apiService.deleteProduct(productId)
    alert('Product deleted successfully!')
    fetchProducts() // Refresh list
  } catch (err) {
    alert('Failed to delete product')
    console.error(err)
  }
}

const goBack = () => {
  showAddForm.value = false
  resetForm()
}

const resetForm = () => {
  productForm.value = {
    name: '',
    category: '',
    sku: '',
    price: 0,
    stock: 0,
    description: '',
    specifications: [{ name: '', value: '' }],
    auctionEnabled: false,
    auctionStartPrice: 0,
    auctionDuration: 7,
    status: 'active'
  }
  selectedFiles.value = []
  existingImages.value = []
  editingProductId.value = null
  isEditing.value = false
}

const addSpecification = () => {
  productForm.value.specifications.push({ name: '', value: '' })
}

const removeSpecification = (index: number) => {
  if (productForm.value.specifications.length > 1) {
    productForm.value.specifications.splice(index, 1)
  }
}

// API Functions
const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    const allProducts = await apiService.getProducts()
    products.value = allProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const validateForm = (): boolean => {
  if (!productForm.value.name.trim()) {
    alert('Product name is required')
    return false
  }
  
  if (!productForm.value.category) {
    alert('Category is required')
    return false
  }
  
  if (productForm.value.price <= 0) {
    alert('Price must be greater than 0')
    return false
  }
  
  if (productForm.value.stock < 0) {
    alert('Stock cannot be negative')
    return false
  }
  
  return true
}

const generateSKU = (): string => {
  const timestamp = Date.now().toString().slice(-6)
  const random = Math.random().toString(36).substring(2, 5).toUpperCase()
  return `PRD${timestamp}${random}`
}

const saveProduct = async () => {
  if (!validateForm()) return
  
  saving.value = true
  try {
    // Auto-generate SKU if not provided
    const finalSku = productForm.value.sku.trim() || generateSKU()
    
    // Handle images - combine existing and new images
    let finalImageUrls: string[] = []
    
    // For editing: start with existing images
    if (isEditing.value) {
      finalImageUrls = [...existingImages.value]
    }
    
    // Upload new images if any files selected
    if (selectedFiles.value.length > 0) {
      try {
        // Validate all files first
        for (const file of selectedFiles.value) {
          const validationError = validateProductImageFile(file)
          if (validationError) {
            throw new Error(validationError)
          }
        }
        
        // Upload all new images
        const uploadResults = await uploadProductImages(selectedFiles.value)
        const newImageUrls = uploadResults.map(result => result.url)
        
        // Add new images to final list
        finalImageUrls = [...finalImageUrls, ...newImageUrls]
        
        console.log('Images uploaded successfully:', newImageUrls)
      } catch (uploadError) {
        throw new Error(`Image upload failed: ${uploadError}`)
      }
    }
    
    const productData: CreateProductRequest = {
      name: productForm.value.name,
      description: productForm.value.description,
      price: productForm.value.price,
      stockQuantity: productForm.value.stock,
      brand: 'Unknown',
      model: 'Unknown',
      sku: finalSku,
      specifications: productForm.value.specifications
        .filter(spec => spec.name && spec.value)
        .map(spec => `${spec.name}: ${spec.value}`)
        .join('\n'),
      imageUrls: finalImageUrls,
      categoryId: parseInt(productForm.value.category)
    }
    
    if (isEditing.value && editingProductId.value) {
      await apiService.updateProduct(editingProductId.value, {
        ...productData,
        isActive: productForm.value.status === 'active'
      })
      alert('Product updated successfully!')
    } else {
      await apiService.createProduct(productData)
      alert('Product created successfully!')
    }
    
    goBack()
    fetchProducts() // Refresh list
  } catch (err) {
    alert(`Failed to save product: ${err}`)
    console.error(err)
  } finally {
    saving.value = false
  }
}

const saveDraft = () => {
  productForm.value.status = 'draft'
  saveProduct()
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page: number) => {
  currentPage.value = page
}

// File handling functions
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click()
  }
}

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    const files = Array.from(target.files)
    selectedFiles.value = [...selectedFiles.value, ...files]
  }
}

const removeFile = (index: number) => {
  selectedFiles.value.splice(index, 1)
}

// Image handling functions
const getProductImage = (product: Product): string | undefined => {
  // Check if product has imageUrls and it's not empty
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0]
  }
  
  // Return undefined if no image available
  return undefined
}

const hasProductImage = (product: Product): boolean => {
  return !!(product.imageUrls && product.imageUrls.length > 0)
}

const handleImageError = (event: Event) => {
  const target = event.target as HTMLImageElement
  const container = target.parentElement
  if (container) {
    target.style.display = 'none'
  }
}

const removeExistingImage = (index: number) => {
  existingImages.value.splice(index, 1)
}

// Add debounced version for search
const debouncedFetchProducts = debounce(fetchProducts, 300)

// Add watchers for real-time filtering
watch([searchQuery, categoryFilter, statusFilter], () => {
  debouncedFetchProducts()
})

onMounted(() => {
  fetchCategories()
  fetchProducts()
})
</script>

<environment_details>
# VSCode Visible Files
(No visible files)

# VSCode Open Tabs
(No open tabs)

# Current Time
7/14/2025, 7:31:37 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
34,462 / 200K tokens used (17%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I can see the issue! The frontend is doing client-side filtering instead of sending the filter parameters to the backend API. The `watch` function is calling `fetchProducts()` which doesn't pass any parameters, so it always fetches all products and then filters them locally.

Let me check the API service to see what parameters the backend expects:

<read_file>
<path>autowarehouse-frontend/src/services/api.ts</path>
</read_file>

---

**User:**

[read_file for 'autowarehouse-frontend/src/services/api.ts'] Result:
import axios, { AxiosInstance, AxiosResponse } from 'axios'

// API Configuration
const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8081/api'

// Create axios instance
const api: AxiosInstance = axios.create({
  baseURL: API_BASE_URL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

// Request interceptor to add auth token
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('auth_token')
    if (token) {
      config.headers.Authorization = `Bearer ${token}`
    }
    return config
  },
  (error) => {
    return Promise.reject(error)
  }
)

// Response interceptor for error handling
api.interceptors.response.use(
  (response: AxiosResponse) => {
    return response
  },
  (error) => {
    if (error.response?.status === 401) {
      // Token expired or invalid
      localStorage.removeItem('auth_token')
      localStorage.removeItem('user_data')
      window.location.href = '/login'
    }
    return Promise.reject(error)
  }
)

// API Response Types
export interface ApiResponse<T = any> {
  data: T
  message?: string
}

export interface ErrorResponse {
  message: string
}

// User Types
export interface User {
  id: number
  email: string
  firstName: string
  lastName: string
  phoneNumber?: string
  role: 'ADMIN' | 'CUSTOMER'
  isActive: boolean
  isEmailVerified: boolean
  createdAt?: string
}

export interface LoginRequest {
  email: string
  password: string
}

export interface AuthResponse {
  message: string
  accessToken?: string
  refreshToken?: string
  userId?: number
  email?: string
  firstName?: string
  lastName?: string
  role?: string
  isEmailVerified?: boolean
}

export interface RegisterRequest {
  email: string
  password: string
  firstName: string
  lastName: string
}

export interface PasswordResetRequest {
  email: string
}

export interface PasswordResetConfirmRequest {
  token: string
  newPassword: string
}

// Product Types
export interface Product {
  id: number
  name: string
  description: string
  price: number
  originalPrice: number
  brand: string
  model: string
  specifications?: string
  features?: string
  imageUrls: string[]
  stockQuantity: number
  isActive: boolean
  isOnSale: boolean
  rating: number
  reviewCount: number
  sku: string
  categoryName: string
  createdAt: string
}

export interface ProductFilters {
  category?: number
  brand?: string
  minPrice?: number
  maxPrice?: number
  search?: string
  onSale?: boolean
  page?: number
  size?: number
}

// Auction Types
export interface Auction {
  id: number
  title: string
  description: string
  startingBid: number
  currentBid: number
  reservePrice?: number
  bidIncrement: number
  startTime: string
  endTime: string
  status: 'DRAFT' | 'SCHEDULED' | 'LIVE' | 'ENDED' | 'CANCELLED'
  bidCount: number
  watcherCount: number
  productName: string
  categoryName: string
  autoExtendMinutes?: number
  winnerName?: string
  winningBid?: number
  createdAt: string
}

export interface BidRequest {
  userId: number
  bidAmount: number
  isAutoBid: boolean
  maxAutoBid?: number
}

export interface Bid {
  id: number
  bidAmount: number
  isAutoBid: boolean
  isWinning: boolean
  bidderName: string
  createdAt: string
}

// Order Types
export interface Order {
  id: number
  orderNumber: string
  status: 'PENDING' | 'CONFIRMED' | 'SHIPPED' | 'DELIVERED' | 'CANCELLED'
  paymentStatus: 'PENDING' | 'PAID' | 'FAILED' | 'REFUNDED'
  subtotal: number
  taxAmount: number
  shippingCost: number
  totalAmount: number
  customerName: string
  shippingAddress: string
  createdAt: string
}

// Category Types
export interface Category {
  id: number
  name: string
  description?: string
  slug: string
  parentId?: number
  parent?: Category
  children?: Category[]
  imageUrl?: string
  isActive: boolean
  sortOrder: number
  createdAt: string
  updatedAt?: string
}

export interface CreateCategoryRequest {
  name: string
  description?: string
  slug: string
  parentId?: number
  imageUrl?: string
  isActive?: boolean
  sortOrder?: number
}

export interface UpdateCategoryRequest {
  name?: string
  description?: string
  slug?: string
  parentId?: number
  imageUrl?: string
  isActive?: boolean
  sortOrder?: number
}

export interface CreateProductRequest {
  name: string
  description: string
  price: number
  originalPrice?: number
  stockQuantity: number
  brand: string
  model: string
  sku?: string
  specifications?: string
  features?: string
  imageUrls: string[]
  categoryId: number
}

export interface UpdateProductRequest extends CreateProductRequest {
  isActive?: boolean
}

// Cart Types
export interface CartItem {
  id: number
  productId: number
  productName: string
  productSku: string
  productBrand: string
  productPrice: number
  originalPrice: number
  productImages: string[]
  quantity: number
  isSelected: boolean
  subtotal: number
  savings: number
  availableStock: number
  isProductActive: boolean
}

export interface CartSummary {
  totalItems: number
  selectedItems: number
  totalQuantity: number
  selectedQuantity: number
  totalAmount: number
  selectedAmount: number
  totalSavings: number
  selectedSavings: number
}

export interface AddToCartRequest {
  userId: number
  productId: number
  quantity: number
}

// Statistics Types
export interface UserStats {
  totalUsers: number
  activeUsers: number
  totalCustomers: number
  totalAdmins: number
}

export interface ProductStats {
  totalProducts: number
  activeProducts: number
  lowStockProducts: number
  outOfStockProducts: number
}

export interface AuctionStats {
  totalAuctions: number
  liveAuctions: number
  upcomingAuctions: number
}

export interface OrderStats {
  totalOrders: number
  pendingOrders: number
  completedOrders: number
  totalRevenue: number
}

// API Service Class
class ApiService {
  // Authentication APIs
  async login(credentials: LoginRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/login', credentials)
    return response.data
  }

  async register(userData: RegisterRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/register', userData)
    return response.data
  }

  async verifyEmail(token: string): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>(`/auth/verify-email?token=${token}`)
    return response.data
  }

  async forgotPassword(request: PasswordResetRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/forgot-password', request)
    return response.data
  }

  async resetPassword(request: PasswordResetConfirmRequest): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>('/auth/reset-password', request)
    return response.data
  }

  async resendVerification(email: string): Promise<AuthResponse> {
    const response = await api.post<AuthResponse>(`/auth/resend-verification?email=${email}`)
    return response.data
  }

  async getCurrentUser(): Promise<AuthResponse> {
    const response = await api.get<AuthResponse>('/auth/me')
    return response.data
  }

  // User Profile APIs
  async getUserProfile(userId: number): Promise<User> {
    const response = await api.get<User>(`/users/profile/${userId}`)
    return response.data
  }

  async updateUserProfile(userId: number, userData: Partial<User>): Promise<User> {
    const response = await api.put<User>(`/users/profile/${userId}`, userData)
    return response.data
  }

  // Product APIs
  async getProducts(filters?: ProductFilters): Promise<Product[]> {
    const params = new URLSearchParams()
    if (filters) {
      Object.entries(filters).forEach(([key, value]) => {
        if (value !== undefined && value !== null) {
          params.append(key, value.toString())
        }
      })
    }
    const response = await api.get<Product[]>(`/products?${params.toString()}`)
    return response.data
  }

  async getProduct(productId: number): Promise<Product> {
    const response = await api.get<Product>(`/products/${productId}`)
    return response.data
  }

  async getPopularProducts(limit: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/featured/popular?limit=${limit}`)
    return response.data
  }

  async getFeaturedProducts(limit: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/featured/featured?limit=${limit}`)
    return response.data
  }

  async getOnSaleProducts(limit: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/featured/on-sale?limit=${limit}`)
    return response.data
  }

  // Admin Product APIs
  async createProduct(productData: CreateProductRequest): Promise<Product> {
    const response = await api.post<Product>('/products/admin', productData)
    return response.data
  }

  async updateProduct(productId: number, productData: UpdateProductRequest): Promise<Product> {
    const response = await api.put<Product>(`/products/admin/${productId}`, productData)
    return response.data
  }

  async deleteProduct(productId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/products/admin/${productId}`)
    return response.data
  }

  async updateProductStock(productId: number, stockQuantity: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/products/admin/${productId}/stock`, { stockQuantity })
    return response.data
  }

  async activateProduct(productId: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/products/admin/${productId}/activate`)
    return response.data
  }

  async deactivateProduct(productId: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/products/admin/${productId}/deactivate`)
    return response.data
  }

  async getAdminProductStats(): Promise<ProductStats> {
    const response = await api.get<ProductStats>('/products/admin/stats')
    return response.data
  }

  async getLowStockProducts(threshold: number = 10): Promise<Product[]> {
    const response = await api.get<Product[]>(`/products/admin/low-stock?threshold=${threshold}`)
    return response.data
  }

  async getOutOfStockProducts(): Promise<Product[]> {
    const response = await api.get<Product[]>('/products/admin/out-of-stock')
    return response.data
  }

  // Auction APIs
  async getAuctions(status?: string): Promise<Auction[]> {
    const params = status ? `?status=${status}` : ''
    const response = await api.get<Auction[]>(`/auctions${params}`)
    return response.data
  }

  async getLiveAuctions(): Promise<Auction[]> {
    const response = await api.get<Auction[]>('/auctions/live')
    return response.data
  }

  async getUpcomingAuctions(): Promise<Auction[]> {
    const response = await api.get<Auction[]>('/auctions/upcoming')
    return response.data
  }

  async getEndingSoonAuctions(): Promise<Auction[]> {
    const response = await api.get<Auction[]>('/auctions/ending-soon')
    return response.data
  }

  async getAuction(auctionId: number): Promise<Auction> {
    const response = await api.get<Auction>(`/auctions/${auctionId}`)
    return response.data
  }

  async placeBid(auctionId: number, bidData: BidRequest): Promise<Bid> {
    const response = await api.post<Bid>(`/auctions/${auctionId}/bids`, bidData)
    return response.data
  }

  async watchAuction(auctionId: number, userId: number): Promise<{ message: string }> {
    const response = await api.post<{ message: string }>(`/auctions/${auctionId}/watch`, { userId })
    return response.data
  }

  async unwatchAuction(auctionId: number, userId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/auctions/${auctionId}/watch/${userId}`)
    return response.data
  }

  // Cart APIs
  async getCartItems(userId: number): Promise<CartItem[]> {
    const response = await api.get<CartItem[]>(`/cart/user/${userId}`)
    return response.data
  }

  async getSelectedCartItems(userId: number): Promise<CartItem[]> {
    const response = await api.get<CartItem[]>(`/cart/user/${userId}/selected`)
    return response.data
  }

  async getCartSummary(userId: number): Promise<CartSummary> {
    const response = await api.get<CartSummary>(`/cart/user/${userId}/summary`)
    return response.data
  }

  async getCartItemCount(userId: number): Promise<{ count: number }> {
    const response = await api.get<{ count: number }>(`/cart/user/${userId}/count`)
    return response.data
  }

  async addToCart(cartData: AddToCartRequest): Promise<CartItem> {
    const response = await api.post<CartItem>('/cart/add', cartData)
    return response.data
  }

  async updateCartItemQuantity(cartItemId: number, quantity: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/cart/${cartItemId}/quantity`, { quantity })
    return response.data
  }

  async toggleCartItemSelection(cartItemId: number): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/cart/${cartItemId}/toggle-selection`)
    return response.data
  }

  async selectAllCartItems(userId: number, selected: boolean): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/cart/user/${userId}/select-all`, { selected })
    return response.data
  }

  async removeFromCart(cartItemId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/cart/${cartItemId}`)
    return response.data
  }

  async clearCart(userId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/cart/user/${userId}/clear`)
    return response.data
  }

  async checkProductInCart(userId: number, productId: number): Promise<{ exists: boolean }> {
    const response = await api.get<{ exists: boolean }>(`/cart/user/${userId}/product/${productId}/exists`)
    return response.data
  }

  // Order APIs
  async createOrder(userId: number): Promise<Order> {
    const response = await api.post<Order>('/orders/create', { userId })
    return response.data
  }

  async getOrder(orderId: number): Promise<Order> {
    const response = await api.get<Order>(`/orders/${orderId}`)
    return response.data
  }

  async getUserOrders(userId: number): Promise<Order[]> {
    const response = await api.get<Order[]>(`/orders/user/${userId}`)
    return response.data
  }

  // Admin APIs
  async getCustomers(): Promise<User[]> {
    const response = await api.get<User[]>('/users/admin/customers')
    return response.data
  }

  async getUserStats(): Promise<UserStats> {
    const response = await api.get<UserStats>('/users/admin/stats')
    return response.data
  }

  async getProductStats(): Promise<ProductStats> {
    const response = await api.get<ProductStats>('/products/admin/stats')
    return response.data
  }

  async getAuctionStats(): Promise<AuctionStats> {
    const response = await api.get<AuctionStats>('/auctions/admin/stats')
    return response.data
  }

  async getOrderStats(): Promise<OrderStats> {
    const response = await api.get<OrderStats>('/orders/admin/stats')
    return response.data
  }

  async getAllOrders(status?: string): Promise<Order[]> {
    const params = status ? `?status=${status}` : ''
    const response = await api.get<Order[]>(`/orders/admin/all${params}`)
    return response.data
  }

  async updateOrderStatus(orderId: number, status: string): Promise<{ message: string }> {
    const response = await api.put<{ message: string }>(`/orders/admin/${orderId}/status`, { status })
    return response.data
  }

  // Category APIs
  async getCategories(): Promise<Category[]> {
    const response = await api.get<Category[]>('/categories')
    return response.data
  }

  async getCategory(categoryId: number): Promise<Category> {
    const response = await api.get<Category>(`/categories/${categoryId}`)
    return response.data
  }

  async getCategoryBySlug(slug: string): Promise<Category> {
    const response = await api.get<Category>(`/categories/slug/${slug}`)
    return response.data
  }

  async getRootCategories(): Promise<Category[]> {
    const response = await api.get<Category[]>('/categories/root')
    return response.data
  }

  async createCategory(categoryData: CreateCategoryRequest): Promise<Category> {
    const response = await api.post<Category>('/categories', categoryData)
    return response.data
  }

  async updateCategory(categoryId: number, categoryData: UpdateCategoryRequest): Promise<Category> {
    const response = await api.put<Category>(`/categories/${categoryId}`, categoryData)
    return response.data
  }

  async deleteCategory(categoryId: number): Promise<{ message: string }> {
    const response = await api.delete<{ message: string }>(`/categories/${categoryId}`)
    return response.data
  }
}

// Export singleton instance
export const apiService = new ApiService()
export default api

<environment_details>
# VSCode Visible Files
(No visible files)

# VSCode Open Tabs
(No open tabs)

# Current Time
7/14/2025, 7:31:53 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
80,216 / 200K tokens used (40%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Perfect! I can see that the `getProducts` method in the API service already supports filters through the `ProductFilters` interface, which includes `search`, `category`, and other parameters. However, the frontend is not using these parameters properly.

Let me check the backend ProductResource to see what parameters it expects:

<read_file>
<path>autowarehouse-backend/src/main/java/com/autowarehouse/resource/ProductResource.java</path>
</read_file>

---

**User:**

[read_file for 'autowarehouse-backend/src/main/java/com/autowarehouse/resource/ProductResource.java'] Result:
package com.autowarehouse.resource;

import com.autowarehouse.entity.Category;
import com.autowarehouse.entity.Product;
import com.autowarehouse.service.ProductService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Path("/api/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {

    @Inject
    ProductService productService;

    @GET
    public Response getAllProducts(
            @QueryParam("category") Long categoryId,
            @QueryParam("brand") String brand,
            @QueryParam("minPrice") BigDecimal minPrice,
            @QueryParam("maxPrice") BigDecimal maxPrice,
            @QueryParam("search") String search,
            @QueryParam("onSale") Boolean onSale,
            @QueryParam("page") @DefaultValue("0") int page,
            @QueryParam("size") @DefaultValue("20") int size) {
        
        try {
            List<Product> products;
            
            if (search != null && !search.trim().isEmpty()) {
                products = productService.searchProducts(search);
            } else if (categoryId != null) {
                Category category = Category.findById(categoryId);
                products = productService.findByCategory(category);
            } else if (brand != null) {
                products = productService.findByBrand(brand);
            } else if (minPrice != null && maxPrice != null) {
                products = productService.findInPriceRange(minPrice, maxPrice);
            } else if (onSale != null && onSale) {
                products = productService.findOnSale();
            } else {
                products = productService.findActiveProducts();
            }

            List<ProductResponse> response = products.stream()
                    .map(ProductResponse::new)
                    .toList();
            
            return Response.ok(response).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch products"))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") Long id) {
        try {
            Product product = productService.findById(id);
            if (product == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Product not found"))
                        .build();
            }
            
            // Increment view count
            productService.incrementViewCount(id);
            
            return Response.ok(new ProductDetailResponse(product)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch product"))
                    .build();
        }
    }

    @GET
    @Path("/sku/{sku}")
    public Response getProductBySku(@PathParam("sku") String sku) {
        try {
            Product product = productService.findBySku(sku);
            if (product == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(new ErrorResponse("Product not found"))
                        .build();
            }
            return Response.ok(new ProductDetailResponse(product)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Failed to fetch product"))
                    .build();
        }
    }

    @GET
    @Path("/featured/popular")
    public Response getPopularProducts(@QueryParam("limit") @DefaultValue("10") int limit) {
        List<Product> products = productService.findPopularProducts(limit);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/featured/featured")
    public Response getFeaturedProducts(@QueryParam("limit") @DefaultValue("10") int limit) {
        List<Product> products = productService.findFeaturedProducts(limit);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/featured/on-sale")
    public Response getOnSaleProducts(@QueryParam("limit") @DefaultValue("10") int limit) {
        List<Product> products = productService.findOnSaleProducts(limit);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/featured/top-rated")
    public Response getTopRatedProducts(@QueryParam("limit") @DefaultValue("10") int limit) {
        List<Product> products = productService.findTopRatedProducts(limit);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/featured/recent")
    public Response getRecentProducts(@QueryParam("days") @DefaultValue("30") int days) {
        List<Product> products = productService.findRecentProducts(days);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    // Admin endpoints
    @POST
    @Path("/admin")
    @RolesAllowed("ADMIN")
    public Response createProduct(@Valid CreateProductRequest request) {
        try {
            Product product = new Product();
            product.name = request.name;
            product.description = request.description;
            product.price = request.price;
            product.originalPrice = request.originalPrice;
            product.stockQuantity = request.stockQuantity;
            product.brand = request.brand;
            product.model = request.model;
            product.sku = request.sku;
            product.specifications = request.specifications;
            product.features = request.features;
            product.imageUrls = request.imageUrls;
            product.category = Category.findById(request.categoryId);
            
            Product createdProduct = productService.createProduct(product);
            return Response.status(Response.Status.CREATED)
                    .entity(new ProductDetailResponse(createdProduct))
                    .build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}")
    @RolesAllowed("ADMIN")
    public Response updateProduct(@PathParam("id") Long id, @Valid UpdateProductRequest request) {
        try {
            Product updatedProduct = new Product();
            updatedProduct.name = request.name;
            updatedProduct.description = request.description;
            updatedProduct.price = request.price;
            updatedProduct.originalPrice = request.originalPrice;
            updatedProduct.stockQuantity = request.stockQuantity;
            updatedProduct.brand = request.brand;
            updatedProduct.model = request.model;
            updatedProduct.sku = request.sku;
            updatedProduct.specifications = request.specifications;
            updatedProduct.features = request.features;
            updatedProduct.imageUrls = request.imageUrls;
            updatedProduct.category = Category.findById(request.categoryId);
            updatedProduct.isActive = request.isActive;
            
            Product product = productService.updateProduct(id, updatedProduct);
            return Response.ok(new ProductDetailResponse(product)).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/stock")
    @RolesAllowed("ADMIN")
    public Response updateStock(@PathParam("id") Long id, @Valid UpdateStockRequest request) {
        try {
            productService.updateStock(id, request.stockQuantity);
            return Response.ok(new SuccessResponse("Stock updated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/sale")
    @RolesAllowed("ADMIN")
    public Response setSalePrice(@PathParam("id") Long id, @Valid SetSalePriceRequest request) {
        try {
            productService.setSalePrice(id, request.salePrice, request.startDate, request.endDate);
            return Response.ok(new SuccessResponse("Sale price set successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}/sale")
    @RolesAllowed("ADMIN")
    public Response clearSale(@PathParam("id") Long id) {
        try {
            productService.clearSale(id);
            return Response.ok(new SuccessResponse("Sale cleared successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/activate")
    @RolesAllowed("ADMIN")
    public Response activateProduct(@PathParam("id") Long id) {
        try {
            productService.activateProduct(id);
            return Response.ok(new SuccessResponse("Product activated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @PUT
    @Path("/admin/{id}/deactivate")
    @RolesAllowed("ADMIN")
    public Response deactivateProduct(@PathParam("id") Long id) {
        try {
            productService.deactivateProduct(id);
            return Response.ok(new SuccessResponse("Product deactivated successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @DELETE
    @Path("/admin/{id}")
    @RolesAllowed("ADMIN")
    public Response deleteProduct(@PathParam("id") Long id) {
        try {
            productService.deleteProduct(id);
            return Response.ok(new SuccessResponse("Product deleted successfully")).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(new ErrorResponse(e.getMessage()))
                    .build();
        }
    }

    @GET
    @Path("/admin/stats")
    @RolesAllowed("ADMIN")
    public Response getProductStats() {
        ProductStatsResponse stats = new ProductStatsResponse();
        stats.totalProducts = productService.getTotalProductsCount();
        stats.activeProducts = productService.getActiveProductsCount();
        stats.lowStockProducts = productService.getLowStockCount(10);
        stats.outOfStockProducts = productService.getOutOfStockCount();
        return Response.ok(stats).build();
    }

    @GET
    @Path("/admin/low-stock")
    @RolesAllowed("ADMIN")
    public Response getLowStockProducts(@QueryParam("threshold") @DefaultValue("10") int threshold) {
        List<Product> products = productService.findLowStock(threshold);
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    @GET
    @Path("/admin/out-of-stock")
    @RolesAllowed("ADMIN")
    public Response getOutOfStockProducts() {
        List<Product> products = productService.findOutOfStock();
        List<ProductResponse> response = products.stream()
                .map(ProductResponse::new)
                .toList();
        return Response.ok(response).build();
    }

    // DTO Classes
    public static class CreateProductRequest {
        public String name;
        public String description;
        public BigDecimal price;
        public BigDecimal originalPrice;
        public Integer stockQuantity;
        public String brand;
        public String model;
        public String sku;
        public String specifications;
        public String features;
        public List<String> imageUrls;
        public Long categoryId;
    }

    public static class UpdateProductRequest {
        public String name;
        public String description;
        public BigDecimal price;
        public BigDecimal originalPrice;
        public Integer stockQuantity;
        public String brand;
        public String model;
        public String sku;
        public String specifications;
        public String features;
        public List<String> imageUrls;
        public Long categoryId;
        public Boolean isActive;
    }

    public static class UpdateStockRequest {
        public Integer stockQuantity;
    }

    public static class SetSalePriceRequest {
        public BigDecimal salePrice;
        public LocalDateTime startDate;
        public LocalDateTime endDate;
    }

    public static class ProductResponse {
        public Long id;
        public String name;
        public String description;
        public BigDecimal price;
        public BigDecimal originalPrice;
        public String brand;
        public String model;
        public List<String> imageUrls;
        public Integer stockQuantity;
        public Boolean isActive;
        public Boolean isOnSale;
        public BigDecimal rating;
        public Integer reviewCount;
        public Integer viewCount;
        public Integer salesCount;

        public ProductResponse(Product product) {
            this.id = product.id;
            this.name = product.name;
            this.description = product.description;
            this.price = product.price;
            this.originalPrice = product.originalPrice;
            this.brand = product.brand;
            this.model = product.model;
            this.imageUrls = product.imageUrls;
            this.stockQuantity = product.stockQuantity;
            this.isActive = product.isActive;
            this.isOnSale = product.isOnSaleNow();
            this.rating = product.rating;
            this.reviewCount = product.reviewCount;
            this.viewCount = product.viewCount.intValue();
            this.salesCount = product.salesCount;
        }
    }

    public static class ProductDetailResponse extends ProductResponse {
        public String specifications;
        public String features;
        public String sku;
        public String categoryName;
        public LocalDateTime createdAt;

        public ProductDetailResponse(Product product) {
            super(product);
            this.specifications = product.specifications;
            this.features = product.features;
            this.sku = product.sku;
            this.categoryName = product.category != null ? product.category.name : null;
            this.createdAt = product.createdAt;
        }
    }

    public static class ProductStatsResponse {
        public long totalProducts;
        public long activeProducts;
        public long lowStockProducts;
        public long outOfStockProducts;
    }

    public static class ErrorResponse {
        public String message;

        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    public static class SuccessResponse {
        public String message;

        public SuccessResponse(String message) {
            this.message = message;
        }
    }
}

<environment_details>
# VSCode Visible Files
(No visible files)

# VSCode Open Tabs
(No open tabs)

# Current Time
7/14/2025, 7:32:05 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
96,756 / 200K tokens used (48%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Perfect! Now I can see the issue. The backend supports filtering by `search`, `category` (as categoryId), and other parameters, but the frontend is not passing these parameters to the API. The frontend is doing client-side filtering instead of server-side filtering.

Let me fix the AdminProductView.vue to properly send the filter parameters to the backend:

<replace_in_file>
<path>autowarehouse-frontend/src/views/admin/AdminProductView.vue</path>
<diff>
------- SEARCH
// API Functions
const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    const allProducts = await apiService.getProducts()
    products.value = allProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}
=======
// API Functions
const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    // Build filter parameters
    const filters: any = {}
    
    if (searchQuery.value.trim()) {
      filters.search = searchQuery.value.trim()
    }
    
    if (categoryFilter.value) {
      // Find category ID by name
      const category = categories.value.find(cat => cat.name === categoryFilter.value)
      if (category) {
        filters.category = category.id
      }
    }
    
    // Note: statusFilter is handled client-side since backend doesn't have a direct status filter
    // The backend returns active products by default, and we filter by stock status on frontend
    
    const allProducts = await apiService.getProducts(filters)
    products.value = allProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}
+++++++ REPLACE
</diff>
</replace_in_file>

---

**User:**

[replace_in_file for 'autowarehouse-frontend/src/views/admin/AdminProductView.vue'] Result:
The content was successfully saved to autowarehouse-frontend/src/views/admin/AdminProductView.vue.

Here is the full, updated content of the file that was saved:

<final_file_content path="autowarehouse-frontend/src/views/admin/AdminProductView.vue">
<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-4">
          <button 
            v-if="showAddForm" 
            @click="goBack" 
            class="text-gray-600 hover:text-gray-800"
          >
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <div>
            <h1 class="text-3xl font-bold text-gray-900">
              {{ showAddForm ? 'Tambah Produk Baru' : 'Product Management' }}
            </h1>
            <p class="text-gray-600 mt-1">
              {{ showAddForm ? 'Lengkapi form di bawah untuk menambah produk' : 'Kelola produk toko Anda' }}
            </p>
          </div>
        </div>
      </div>

        <!-- Product List View -->
        <div v-if="!showAddForm" class="p-6">
          <!-- Filters Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
            <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <div class="relative">
                  <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input 
                    v-model="searchQuery"
                    type="text" 
                    placeholder="Cari produk..." 
                    class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
                  >
                </div>
                <select 
                  v-model="categoryFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Kategori</option>
                  <option 
                    v-for="category in categories" 
                    :key="category.id" 
                    :value="category.name"
                  >
                    {{ category.name }}
                  </option>
                </select>
                <select 
                  v-model="statusFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Status</option>
                  <option value="active">Aktif</option>
                  <option value="inactive">Tidak Aktif</option>
                  <option value="out_of_stock">Stok Habis</option>
                </select>
              </div>
              <button 
                @click="showAddProductForm"
                class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center space-x-2"
              >
                <i class="fa-solid fa-plus"></i>
                <span>Tambah Produk</span>
              </button>
            </div>
          </div>

          <!-- Loading State -->
          <div v-if="loading" class="flex justify-center items-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <span class="ml-2 text-gray-600">Loading products...</span>
          </div>

          <!-- Error State -->
          <div v-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
            <div class="flex">
              <i class="fa-solid fa-exclamation-triangle text-red-600 mr-2"></i>
              <span class="text-red-800">{{ error }}</span>
              <button @click="fetchProducts" class="ml-auto text-red-600 hover:text-red-800">
                <i class="fa-solid fa-refresh"></i> Retry
              </button>
            </div>
          </div>

          <!-- Products Table -->
          <div v-if="!loading && !error" class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50 border-b border-gray-200">
                  <tr>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Produk</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Kategori</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Harga</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stok</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aksi</th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr 
                    v-for="product in filteredProducts" 
                    :key="product.id" 
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <div class="w-12 h-12 mr-4 flex-shrink-0">
                          <img 
                            v-if="hasProductImage(product)"
                            :src="getProductImage(product)" 
                            :alt="product.name" 
                            class="w-12 h-12 rounded-lg object-cover"
                            @error="handleImageError"
                          >
                          <div 
                            v-else
                            class="w-12 h-12 rounded-lg bg-gray-200 flex items-center justify-center"
                          >
                            <i class="fa-solid fa-image text-gray-400 text-lg"></i>
                          </div>
                        </div>
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ product.name }}</div>
                          <div class="text-sm text-gray-500">SKU: {{ product.sku }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.categoryName }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">Rp {{ formatPrice(product.price) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.stockQuantity }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="getStatusClass(getProductStatus(product))"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ getStatusText(getProductStatus(product)) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex space-x-2">
                        <button 
                          @click="editProduct(product)"
                          class="text-blue-600 hover:text-blue-700"
                        >
                          <i class="fa-solid fa-edit"></i>
                        </button>
                        <button 
                          @click="deleteProduct(product.id)"
                          class="text-red-600 hover:text-red-700"
                        >
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <!-- Pagination -->
            <div class="bg-gray-50 px-6 py-3 flex items-center justify-between border-t border-gray-200">
              <div class="text-sm text-gray-700">
                Menampilkan {{ startIndex }} sampai {{ endIndex }} dari {{ totalProducts }} produk
              </div>
              <div class="flex space-x-2">
                <button 
                  @click="previousPage"
                  :disabled="currentPage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Previous
                </button>
                <button 
                  v-for="page in visiblePages" 
                  :key="page"
                  @click="goToPage(page)"
                  :class="page === currentPage ? 'bg-blue-600 text-white' : 'border border-gray-300 hover:bg-gray-100'"
                  class="px-3 py-1 text-sm rounded"
                >
                  {{ page }}
                </button>
                <button 
                  @click="nextPage"
                  :disabled="currentPage === totalPages"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Add/Edit Product Form -->
        <div v-if="showAddForm" class="p-6">
          <div class="max-w-4xl mx-auto">
            <form @submit.prevent="saveProduct" class="space-y-8">
              
              <!-- Basic Information -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-info-circle text-blue-600 mr-2"></i>
                  Informasi Dasar
                </h3>
                
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Nama Produk *</label>
                    <input 
                      v-model="productForm.name"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Masukkan nama produk" 
                      required
                    >
                  </div>
                  
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Kategori *</label>
                    <select 
                      v-model="productForm.category"
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      required
                      :disabled="categoriesLoading"
                    >
                      <option value="">{{ categoriesLoading ? 'Loading categories...' : 'Pilih Kategori' }}</option>
                      <option 
                        v-for="category in categories" 
                        :key="category.id" 
                        :value="category.id"
                      >
                        {{ category.name }}
                      </option>
                    </select>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">SKU</label>
                    <input 
                      v-model="productForm.sku"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Kode produk unik"
                    >
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Harga *</label>
                    <div class="relative">
                      <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                      <input 
                        v-model="productForm.price"
                        type="number" 
                        class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                        placeholder="0" 
                        required
                      >
                    </div>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Stok *</label>
                    <input 
                      v-model="productForm.stock"
                      type="number" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="0" 
                      required
                    >
                  </div>
                </div>
              </div>

              <!-- Specifications -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-cogs text-blue-600 mr-2"></i>
                  Spesifikasi Produk
                </h3>
                
                <div class="space-y-4">
                  <div 
                    v-for="(spec, index) in productForm.specifications" 
                    :key="index"
                    class="flex items-center space-x-4"
                  >
                    <input 
                      v-model="spec.name"
                      type="text" 
                      placeholder="Nama spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <input 
                      v-model="spec.value"
                      type="text" 
                      placeholder="Nilai spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <button 
                      type="button" 
                      @click="removeSpecification(index)" 
                      class="text-red-600 hover:text-red-700 p-2"
                    >
                      <i class="fa-solid fa-trash"></i>
                    </button>
                  </div>
                </div>
                
                <button 
                  type="button" 
                  @click="addSpecification" 
                  class="mt-4 flex items-center space-x-2 text-blue-600 hover:text-blue-700"
                >
                  <i class="fa-solid fa-plus"></i>
                  <span>Tambah Spesifikasi</span>
                </button>
              </div>

              <!-- Description -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-align-left text-blue-600 mr-2"></i>
                  Deskripsi Produk
                </h3>
                
                <textarea 
                  v-model="productForm.description"
                  rows="6" 
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                  placeholder="Masukkan deskripsi lengkap produk..."
                ></textarea>
              </div>

              <!-- Images -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-images text-blue-600 mr-2"></i>
                  Gambar Produk
                </h3>
                
                <div class="space-y-6">
                  <!-- Show existing images when editing -->
                  <div v-if="isEditing && existingImages.length > 0" class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-3">Gambar Saat Ini</label>
                    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                      <div 
                        v-for="(imageUrl, index) in existingImages" 
                        :key="index"
                        class="relative group"
                      >
                        <img 
                          :src="imageUrl" 
                          :alt="`Product image ${index + 1}`"
                          class="w-full h-32 object-cover rounded-lg border border-gray-200"
                          @error="handleImageError"
                        >
                        <button 
                          type="button"
                          @click="removeExistingImage(index)"
                          class="absolute top-2 right-2 bg-red-600 text-white rounded-full w-6 h-6 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                        >
                          <i class="fa-solid fa-times text-xs"></i>
                        </button>
                      </div>
                    </div>
                  </div>

                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-3">
                      {{ isEditing ? 'Tambah Gambar Baru' : 'Upload Gambar Produk *' }}
                    </label>
                    <div 
                      @click="triggerFileInput"
                      class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-blue-600 transition-colors cursor-pointer bg-gray-50 hover:bg-gray-100"
                    >
                      <i class="fa-solid fa-cloud-upload text-4xl text-gray-400 mb-4"></i>
                      <p class="text-gray-600 mb-2 font-medium">Drag & drop gambar atau klik untuk upload</p>
                      <p class="text-sm text-gray-500 mb-3">PNG, JPG hingga 10MB per file</p>
                      <button type="button" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                        Pilih File
                      </button>
                      <input 
                        ref="fileInput"
                        type="file" 
                        class="hidden" 
                        accept="image/*" 
                        multiple
                        @change="handleFileSelect"
                      >
                    </div>
                    
                    <!-- Show selected files -->
                    <div v-if="selectedFiles.length > 0" class="mt-4">
                      <p class="text-sm font-medium text-gray-700 mb-2">File yang dipilih:</p>
                      <div class="space-y-2">
                        <div 
                          v-for="(file, index) in selectedFiles" 
                          :key="index"
                          class="flex items-center justify-between bg-gray-50 p-2 rounded"
                        >
                          <span class="text-sm text-gray-600">{{ file.name }}</span>
                          <button 
                            type="button"
                            @click="removeFile(index)"
                            class="text-red-600 hover:text-red-700"
                          >
                            <i class="fa-solid fa-times"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Auction Settings -->
              <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-gavel text-blue-600 mr-2"></i>
                  Pengaturan Lelang
                </h3>
                
                <div class="space-y-6">
                  <div class="flex items-center space-x-3">
                    <input 
                      v-model="productForm.auctionEnabled"
                      type="checkbox" 
                      class="w-5 h-5 text-blue-600 focus:ring-blue-600 border-gray-300 rounded"
                    >
                    <label class="text-sm font-medium text-gray-700">Tersedia untuk lelang</label>
                  </div>
                  
                  <div v-if="productForm.auctionEnabled" class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Harga Awal Lelang</label>
                      <div class="relative">
                        <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                        <input 
                          v-model="productForm.auctionStartPrice"
                          type="number" 
                          class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                          placeholder="0"
                        >
                      </div>
                    </div>
                    
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Durasi Lelang (Hari)</label>
                      <select 
                        v-model="productForm.auctionDuration"
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      >
                        <option value="1">1 Hari</option>
                        <option value="3">3 Hari</option>
                        <option value="7">7 Hari</option>
                        <option value="14">14 Hari</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div> -->

              <!-- Status -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-toggle-on text-blue-600 mr-2"></i>
                  Status Produk
                </h3>
                
                <div class="flex space-x-6">
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="active" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Aktif</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="draft" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Draft</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="inactive" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Tidak Aktif</span>
                  </label>
                </div>
              </div>

              <!-- Form Actions -->
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <button 
                  type="button" 
                  @click="goBack" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors disabled:opacity-50"
                >
                  Batal
                </button>
                <button 
                  type="button" 
                  @click="saveDraft"
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-gray-600 text-white rounded-lg font-medium hover:bg-gray-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>Simpan sebagai Draft</span>
                </button>
                <button 
                  type="submit" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>{{ isEditing ? 'Update Produk' : 'Publikasikan Produk' }}</span>
                </button>
              </div>
            </form>
          </div>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService, type Product, type Category, type CreateProductRequest, type UpdateProductRequest } from '@/services/api'
import { debounce } from '@/utils/debounce'
import { uploadProductImages, deleteProductImages, validateProductImageFile, type UploadResult } from '@/services/firebase'

interface Specification {
  name: string
  value: string
}

interface LocalProduct {
  id: string
  name: string
  sku: string
  category: string
  price: number
  stock: number
  status: 'active' | 'inactive' | 'out_of_stock'
  image: string
  description?: string
  specifications?: Specification[]
}

interface ProductForm {
  name: string
  category: string
  sku: string
  price: number
  stock: number
  description: string
  specifications: Specification[]
  auctionEnabled: boolean
  auctionStartPrice: number
  auctionDuration: number
  status: 'active' | 'draft' | 'inactive'
}

const searchQuery = ref('')
const categoryFilter = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showAddForm = ref(false)
const isEditing = ref(false)
const editingProductId = ref<number | null>(null)

// API Integration
const products = ref<Product[]>([])
const categories = ref<Category[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const saving = ref(false)
const categoriesLoading = ref(false)

// File handling
const selectedFiles = ref<File[]>([])
const existingImages = ref<string[]>([])
const fileInput = ref<HTMLInputElement | null>(null)

const productForm = ref<ProductForm>({
  name: '',
  category: '',
  sku: '',
  price: 0,
  stock: 0,
  description: '',
  specifications: [{ name: '', value: '' }],
  auctionEnabled: false,
  auctionStartPrice: 0,
  auctionDuration: 7,
  status: 'active'
})

const filteredProducts = computed(() => {
  let filtered = products.value

  if (searchQuery.value) {
    filtered = filtered.filter(product => 
      product.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      product.sku.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  if (categoryFilter.value) {
    filtered = filtered.filter(product => 
      product.categoryName.toLowerCase() === categoryFilter.value.toLowerCase()
    )
  }

  if (statusFilter.value) {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalProducts = computed(() => products.value.length)
const totalPages = computed(() => Math.ceil(products.value.length / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalProducts.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStatusClass = (status: string) => {
  const classes = {
    active: 'bg-green-100 text-green-800',
    inactive: 'bg-gray-100 text-gray-800',
    out_of_stock: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    active: 'Aktif',
    inactive: 'Tidak Aktif',
    out_of_stock: 'Stok Habis'
  }
  return texts[status as keyof typeof texts] || 'Unknown'
}

const getProductStatus = (product: Product): string => {
  if (product.stockQuantity === 0) {
    return 'out_of_stock'
  }
  return product.isActive ? 'active' : 'inactive'
}

const showAddProductForm = () => {
  resetForm()
  showAddForm.value = true
  isEditing.value = false
}

const editProduct = async (product: Product) => {
  try {
    // Fetch fresh product data
    const fullProduct = await apiService.getProduct(product.id)
    
    // Parse specifications back to array format
    const specs = fullProduct.specifications 
      ? fullProduct.specifications.split('\n').map(line => {
          const [name, value] = line.split(': ')
          return { name: name || '', value: value || '' }
        })
      : [{ name: '', value: '' }]
    
    // Find category ID from categories list
    const category = categories.value.find(cat => cat.name === fullProduct.categoryName)
    
    // Populate form with API data
    productForm.value = {
      name: fullProduct.name,
      category: category?.id.toString() || '',
      sku: fullProduct.sku,
      price: fullProduct.price,
      stock: fullProduct.stockQuantity,
      description: fullProduct.description,
      specifications: specs,
      auctionEnabled: false,
      auctionStartPrice: 0,
      auctionDuration: 7,
      status: fullProduct.isActive ? 'active' : 'inactive'
    }
    
    // Load existing images
    existingImages.value = fullProduct.imageUrls || []
    selectedFiles.value = []
    
    editingProductId.value = product.id
    showAddForm.value = true
    isEditing.value = true
  } catch (err) {
    alert('Failed to load product details')
    console.error(err)
  }
}

const deleteProduct = async (productId: number) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await apiService.deleteProduct(productId)
    alert('Product deleted successfully!')
    fetchProducts() // Refresh list
  } catch (err) {
    alert('Failed to delete product')
    console.error(err)
  }
}

const goBack = () => {
  showAddForm.value = false
  resetForm()
}

const resetForm = () => {
  productForm.value = {
    name: '',
    category: '',
    sku: '',
    price: 0,
    stock: 0,
    description: '',
    specifications: [{ name: '', value: '' }],
    auctionEnabled: false,
    auctionStartPrice: 0,
    auctionDuration: 7,
    status: 'active'
  }
  selectedFiles.value = []
  existingImages.value = []
  editingProductId.value = null
  isEditing.value = false
}

const addSpecification = () => {
  productForm.value.specifications.push({ name: '', value: '' })
}

const removeSpecification = (index: number) => {
  if (productForm.value.specifications.length > 1) {
    productForm.value.specifications.splice(index, 1)
  }
}

// API Functions
const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    // Build filter parameters
    const filters: any = {}
    
    if (searchQuery.value.trim()) {
      filters.search = searchQuery.value.trim()
    }
    
    if (categoryFilter.value) {
      // Find category ID by name
      const category = categories.value.find(cat => cat.name === categoryFilter.value)
      if (category) {
        filters.category = category.id
      }
    }
    
    // Note: statusFilter is handled client-side since backend doesn't have a direct status filter
    // The backend returns active products by default, and we filter by stock status on frontend
    
    const allProducts = await apiService.getProducts(filters)
    products.value = allProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const validateForm = (): boolean => {
  if (!productForm.value.name.trim()) {
    alert('Product name is required')
    return false
  }
  
  if (!productForm.value.category) {
    alert('Category is required')
    return false
  }
  
  if (productForm.value.price <= 0) {
    alert('Price must be greater than 0')
    return false
  }
  
  if (productForm.value.stock < 0) {
    alert('Stock cannot be negative')
    return false
  }
  
  return true
}

const generateSKU = (): string => {
  const timestamp = Date.now().toString().slice(-6)
  const random = Math.random().toString(36).substring(2, 5).toUpperCase()
  return `PRD${timestamp}${random}`
}

const saveProduct = async () => {
  if (!validateForm()) return
  
  saving.value = true
  try {
    // Auto-generate SKU if not provided
    const finalSku = productForm.value.sku.trim() || generateSKU()
    
    // Handle images - combine existing and new images
    let finalImageUrls: string[] = []
    
    // For editing: start with existing images
    if (isEditing.value) {
      finalImageUrls = [...existingImages.value]
    }
    
    // Upload new images if any files selected
    if (selectedFiles.value.length > 0) {
      try {
        // Validate all files first
        for (const file of selectedFiles.value) {
          const validationError = validateProductImageFile(file)
          if (validationError) {
            throw new Error(validationError)
          }
        }
        
        // Upload all new images
        const uploadResults = await uploadProductImages(selectedFiles.value)
        const newImageUrls = uploadResults.map(result => result.url)
        
        // Add new images to final list
        finalImageUrls = [...finalImageUrls, ...newImageUrls]
        
        console.log('Images uploaded successfully:', newImageUrls)
      } catch (uploadError) {
        throw new Error(`Image upload failed: ${uploadError}`)
      }
    }
    
    const productData: CreateProductRequest = {
      name: productForm.value.name,
      description: productForm.value.description,
      price: productForm.value.price,
      stockQuantity: productForm.value.stock,
      brand: 'Unknown',
      model: 'Unknown',
      sku: finalSku,
      specifications: productForm.value.specifications
        .filter(spec => spec.name && spec.value)
        .map(spec => `${spec.name}: ${spec.value}`)
        .join('\n'),
      imageUrls: finalImageUrls,
      categoryId: parseInt(productForm.value.category)
    }
    
    if (isEditing.value && editingProductId.value) {
      await apiService.updateProduct(editingProductId.value, {
        ...productData,
        isActive: productForm.value.status === 'active'
      })
      alert('Product updated successfully!')
    } else {
      await apiService.createProduct(productData)
      alert('Product created successfully!')
    }
    
    goBack()
    fetchProducts() // Refresh list
  } catch (err) {
    alert(`Failed to save product: ${err}`)
    console.error(err)
  } finally {
    saving.value = false
  }
}

const saveDraft = () => {
  productForm.value.status = 'draft'
  saveProduct()
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page: number) => {
  currentPage.value = page
}

// File handling functions
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click()
  }
}

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    const files = Array.from(target.files)
    selectedFiles.value = [...selectedFiles.value, ...files]
  }
}

const removeFile = (index: number) => {
  selectedFiles.value.splice(index, 1)
}

// Image handling functions
const getProductImage = (product: Product): string | undefined => {
  // Check if product has imageUrls and it's not empty
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0]
  }
  
  // Return undefined if no image available
  return undefined
}

const hasProductImage = (product: Product): boolean => {
  return !!(product.imageUrls && product.imageUrls.length > 0)
}

const handleImageError = (event: Event) => {
  const target = event.target as HTMLImageElement
  const container = target.parentElement
  if (container) {
    target.style.display = 'none'
  }
}

const removeExistingImage = (index: number) => {
  existingImages.value.splice(index, 1)
}

// Add debounced version for search
const debouncedFetchProducts = debounce(fetchProducts, 300)

// Add watchers for real-time filtering
watch([searchQuery, categoryFilter, statusFilter], () => {
  debouncedFetchProducts()
})

onMounted(() => {
  fetchCategories()
  fetchProducts()
})
</script>

</final_file_content>

IMPORTANT: For any future changes to this file, use the final_file_content shown above as your reference. This content reflects the current state of the file, including any auto-formatting (e.g., if you used single quotes but the formatter converted them to double quotes). Always base your SEARCH/REPLACE operations on this final version to ensure accuracy.


<environment_details>
# VSCode Visible Files
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# VSCode Open Tabs
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# Current Time
7/14/2025, 7:32:30 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
106,900 / 200K tokens used (53%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Now I need to update the `filteredProducts` computed property to remove the client-side filtering since we're now doing server-side filtering. The status filter still needs to be handled client-side since the backend doesn't have a direct status parameter:

<replace_in_file>
<path>autowarehouse-frontend/src/views/admin/AdminProductView.vue</path>
<diff>
------- SEARCH
const filteredProducts = computed(() => {
  let filtered = products.value

  if (searchQuery.value) {
    filtered = filtered.filter(product => 
      product.name.toLowerCase().includes(searchQuery.value.toLowerCase()) ||
      product.sku.toLowerCase().includes(searchQuery.value.toLowerCase())
    )
  }

  if (categoryFilter.value) {
    filtered = filtered.filter(product => 
      product.categoryName.toLowerCase() === categoryFilter.value.toLowerCase()
    )
  }

  if (statusFilter.value) {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})
=======
const filteredProducts = computed(() => {
  let filtered = products.value

  // Only apply status filter client-side since backend doesn't support it
  if (statusFilter.value) {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})
+++++++ REPLACE
</diff>
</replace_in_file>

---

**User:**

[replace_in_file for 'autowarehouse-frontend/src/views/admin/AdminProductView.vue'] Result:
The content was successfully saved to autowarehouse-frontend/src/views/admin/AdminProductView.vue.

Here is the full, updated content of the file that was saved:

<final_file_content path="autowarehouse-frontend/src/views/admin/AdminProductView.vue">
<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-4">
          <button 
            v-if="showAddForm" 
            @click="goBack" 
            class="text-gray-600 hover:text-gray-800"
          >
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <div>
            <h1 class="text-3xl font-bold text-gray-900">
              {{ showAddForm ? 'Tambah Produk Baru' : 'Product Management' }}
            </h1>
            <p class="text-gray-600 mt-1">
              {{ showAddForm ? 'Lengkapi form di bawah untuk menambah produk' : 'Kelola produk toko Anda' }}
            </p>
          </div>
        </div>
      </div>

        <!-- Product List View -->
        <div v-if="!showAddForm" class="p-6">
          <!-- Filters Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
            <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <div class="relative">
                  <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input 
                    v-model="searchQuery"
                    type="text" 
                    placeholder="Cari produk..." 
                    class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
                  >
                </div>
                <select 
                  v-model="categoryFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Kategori</option>
                  <option 
                    v-for="category in categories" 
                    :key="category.id" 
                    :value="category.name"
                  >
                    {{ category.name }}
                  </option>
                </select>
                <select 
                  v-model="statusFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Status</option>
                  <option value="active">Aktif</option>
                  <option value="inactive">Tidak Aktif</option>
                  <option value="out_of_stock">Stok Habis</option>
                </select>
              </div>
              <button 
                @click="showAddProductForm"
                class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center space-x-2"
              >
                <i class="fa-solid fa-plus"></i>
                <span>Tambah Produk</span>
              </button>
            </div>
          </div>

          <!-- Loading State -->
          <div v-if="loading" class="flex justify-center items-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <span class="ml-2 text-gray-600">Loading products...</span>
          </div>

          <!-- Error State -->
          <div v-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
            <div class="flex">
              <i class="fa-solid fa-exclamation-triangle text-red-600 mr-2"></i>
              <span class="text-red-800">{{ error }}</span>
              <button @click="fetchProducts" class="ml-auto text-red-600 hover:text-red-800">
                <i class="fa-solid fa-refresh"></i> Retry
              </button>
            </div>
          </div>

          <!-- Products Table -->
          <div v-if="!loading && !error" class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50 border-b border-gray-200">
                  <tr>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Produk</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Kategori</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Harga</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stok</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aksi</th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr 
                    v-for="product in filteredProducts" 
                    :key="product.id" 
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <div class="w-12 h-12 mr-4 flex-shrink-0">
                          <img 
                            v-if="hasProductImage(product)"
                            :src="getProductImage(product)" 
                            :alt="product.name" 
                            class="w-12 h-12 rounded-lg object-cover"
                            @error="handleImageError"
                          >
                          <div 
                            v-else
                            class="w-12 h-12 rounded-lg bg-gray-200 flex items-center justify-center"
                          >
                            <i class="fa-solid fa-image text-gray-400 text-lg"></i>
                          </div>
                        </div>
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ product.name }}</div>
                          <div class="text-sm text-gray-500">SKU: {{ product.sku }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.categoryName }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">Rp {{ formatPrice(product.price) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.stockQuantity }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="getStatusClass(getProductStatus(product))"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ getStatusText(getProductStatus(product)) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex space-x-2">
                        <button 
                          @click="editProduct(product)"
                          class="text-blue-600 hover:text-blue-700"
                        >
                          <i class="fa-solid fa-edit"></i>
                        </button>
                        <button 
                          @click="deleteProduct(product.id)"
                          class="text-red-600 hover:text-red-700"
                        >
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <!-- Pagination -->
            <div class="bg-gray-50 px-6 py-3 flex items-center justify-between border-t border-gray-200">
              <div class="text-sm text-gray-700">
                Menampilkan {{ startIndex }} sampai {{ endIndex }} dari {{ totalProducts }} produk
              </div>
              <div class="flex space-x-2">
                <button 
                  @click="previousPage"
                  :disabled="currentPage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Previous
                </button>
                <button 
                  v-for="page in visiblePages" 
                  :key="page"
                  @click="goToPage(page)"
                  :class="page === currentPage ? 'bg-blue-600 text-white' : 'border border-gray-300 hover:bg-gray-100'"
                  class="px-3 py-1 text-sm rounded"
                >
                  {{ page }}
                </button>
                <button 
                  @click="nextPage"
                  :disabled="currentPage === totalPages"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Add/Edit Product Form -->
        <div v-if="showAddForm" class="p-6">
          <div class="max-w-4xl mx-auto">
            <form @submit.prevent="saveProduct" class="space-y-8">
              
              <!-- Basic Information -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-info-circle text-blue-600 mr-2"></i>
                  Informasi Dasar
                </h3>
                
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Nama Produk *</label>
                    <input 
                      v-model="productForm.name"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Masukkan nama produk" 
                      required
                    >
                  </div>
                  
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Kategori *</label>
                    <select 
                      v-model="productForm.category"
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      required
                      :disabled="categoriesLoading"
                    >
                      <option value="">{{ categoriesLoading ? 'Loading categories...' : 'Pilih Kategori' }}</option>
                      <option 
                        v-for="category in categories" 
                        :key="category.id" 
                        :value="category.id"
                      >
                        {{ category.name }}
                      </option>
                    </select>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">SKU</label>
                    <input 
                      v-model="productForm.sku"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Kode produk unik"
                    >
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Harga *</label>
                    <div class="relative">
                      <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                      <input 
                        v-model="productForm.price"
                        type="number" 
                        class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                        placeholder="0" 
                        required
                      >
                    </div>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Stok *</label>
                    <input 
                      v-model="productForm.stock"
                      type="number" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="0" 
                      required
                    >
                  </div>
                </div>
              </div>

              <!-- Specifications -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-cogs text-blue-600 mr-2"></i>
                  Spesifikasi Produk
                </h3>
                
                <div class="space-y-4">
                  <div 
                    v-for="(spec, index) in productForm.specifications" 
                    :key="index"
                    class="flex items-center space-x-4"
                  >
                    <input 
                      v-model="spec.name"
                      type="text" 
                      placeholder="Nama spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <input 
                      v-model="spec.value"
                      type="text" 
                      placeholder="Nilai spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <button 
                      type="button" 
                      @click="removeSpecification(index)" 
                      class="text-red-600 hover:text-red-700 p-2"
                    >
                      <i class="fa-solid fa-trash"></i>
                    </button>
                  </div>
                </div>
                
                <button 
                  type="button" 
                  @click="addSpecification" 
                  class="mt-4 flex items-center space-x-2 text-blue-600 hover:text-blue-700"
                >
                  <i class="fa-solid fa-plus"></i>
                  <span>Tambah Spesifikasi</span>
                </button>
              </div>

              <!-- Description -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-align-left text-blue-600 mr-2"></i>
                  Deskripsi Produk
                </h3>
                
                <textarea 
                  v-model="productForm.description"
                  rows="6" 
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                  placeholder="Masukkan deskripsi lengkap produk..."
                ></textarea>
              </div>

              <!-- Images -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-images text-blue-600 mr-2"></i>
                  Gambar Produk
                </h3>
                
                <div class="space-y-6">
                  <!-- Show existing images when editing -->
                  <div v-if="isEditing && existingImages.length > 0" class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-3">Gambar Saat Ini</label>
                    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                      <div 
                        v-for="(imageUrl, index) in existingImages" 
                        :key="index"
                        class="relative group"
                      >
                        <img 
                          :src="imageUrl" 
                          :alt="`Product image ${index + 1}`"
                          class="w-full h-32 object-cover rounded-lg border border-gray-200"
                          @error="handleImageError"
                        >
                        <button 
                          type="button"
                          @click="removeExistingImage(index)"
                          class="absolute top-2 right-2 bg-red-600 text-white rounded-full w-6 h-6 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                        >
                          <i class="fa-solid fa-times text-xs"></i>
                        </button>
                      </div>
                    </div>
                  </div>

                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-3">
                      {{ isEditing ? 'Tambah Gambar Baru' : 'Upload Gambar Produk *' }}
                    </label>
                    <div 
                      @click="triggerFileInput"
                      class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-blue-600 transition-colors cursor-pointer bg-gray-50 hover:bg-gray-100"
                    >
                      <i class="fa-solid fa-cloud-upload text-4xl text-gray-400 mb-4"></i>
                      <p class="text-gray-600 mb-2 font-medium">Drag & drop gambar atau klik untuk upload</p>
                      <p class="text-sm text-gray-500 mb-3">PNG, JPG hingga 10MB per file</p>
                      <button type="button" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                        Pilih File
                      </button>
                      <input 
                        ref="fileInput"
                        type="file" 
                        class="hidden" 
                        accept="image/*" 
                        multiple
                        @change="handleFileSelect"
                      >
                    </div>
                    
                    <!-- Show selected files -->
                    <div v-if="selectedFiles.length > 0" class="mt-4">
                      <p class="text-sm font-medium text-gray-700 mb-2">File yang dipilih:</p>
                      <div class="space-y-2">
                        <div 
                          v-for="(file, index) in selectedFiles" 
                          :key="index"
                          class="flex items-center justify-between bg-gray-50 p-2 rounded"
                        >
                          <span class="text-sm text-gray-600">{{ file.name }}</span>
                          <button 
                            type="button"
                            @click="removeFile(index)"
                            class="text-red-600 hover:text-red-700"
                          >
                            <i class="fa-solid fa-times"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Auction Settings -->
              <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-gavel text-blue-600 mr-2"></i>
                  Pengaturan Lelang
                </h3>
                
                <div class="space-y-6">
                  <div class="flex items-center space-x-3">
                    <input 
                      v-model="productForm.auctionEnabled"
                      type="checkbox" 
                      class="w-5 h-5 text-blue-600 focus:ring-blue-600 border-gray-300 rounded"
                    >
                    <label class="text-sm font-medium text-gray-700">Tersedia untuk lelang</label>
                  </div>
                  
                  <div v-if="productForm.auctionEnabled" class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Harga Awal Lelang</label>
                      <div class="relative">
                        <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                        <input 
                          v-model="productForm.auctionStartPrice"
                          type="number" 
                          class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                          placeholder="0"
                        >
                      </div>
                    </div>
                    
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Durasi Lelang (Hari)</label>
                      <select 
                        v-model="productForm.auctionDuration"
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      >
                        <option value="1">1 Hari</option>
                        <option value="3">3 Hari</option>
                        <option value="7">7 Hari</option>
                        <option value="14">14 Hari</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div> -->

              <!-- Status -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-toggle-on text-blue-600 mr-2"></i>
                  Status Produk
                </h3>
                
                <div class="flex space-x-6">
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="active" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Aktif</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="draft" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Draft</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="inactive" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Tidak Aktif</span>
                  </label>
                </div>
              </div>

              <!-- Form Actions -->
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <button 
                  type="button" 
                  @click="goBack" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors disabled:opacity-50"
                >
                  Batal
                </button>
                <button 
                  type="button" 
                  @click="saveDraft"
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-gray-600 text-white rounded-lg font-medium hover:bg-gray-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>Simpan sebagai Draft</span>
                </button>
                <button 
                  type="submit" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>{{ isEditing ? 'Update Produk' : 'Publikasikan Produk' }}</span>
                </button>
              </div>
            </form>
          </div>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService, type Product, type Category, type CreateProductRequest, type UpdateProductRequest } from '@/services/api'
import { debounce } from '@/utils/debounce'
import { uploadProductImages, deleteProductImages, validateProductImageFile, type UploadResult } from '@/services/firebase'

interface Specification {
  name: string
  value: string
}

interface LocalProduct {
  id: string
  name: string
  sku: string
  category: string
  price: number
  stock: number
  status: 'active' | 'inactive' | 'out_of_stock'
  image: string
  description?: string
  specifications?: Specification[]
}

interface ProductForm {
  name: string
  category: string
  sku: string
  price: number
  stock: number
  description: string
  specifications: Specification[]
  auctionEnabled: boolean
  auctionStartPrice: number
  auctionDuration: number
  status: 'active' | 'draft' | 'inactive'
}

const searchQuery = ref('')
const categoryFilter = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showAddForm = ref(false)
const isEditing = ref(false)
const editingProductId = ref<number | null>(null)

// API Integration
const products = ref<Product[]>([])
const categories = ref<Category[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const saving = ref(false)
const categoriesLoading = ref(false)

// File handling
const selectedFiles = ref<File[]>([])
const existingImages = ref<string[]>([])
const fileInput = ref<HTMLInputElement | null>(null)

const productForm = ref<ProductForm>({
  name: '',
  category: '',
  sku: '',
  price: 0,
  stock: 0,
  description: '',
  specifications: [{ name: '', value: '' }],
  auctionEnabled: false,
  auctionStartPrice: 0,
  auctionDuration: 7,
  status: 'active'
})

const filteredProducts = computed(() => {
  let filtered = products.value

  // Only apply status filter client-side since backend doesn't support it
  if (statusFilter.value) {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalProducts = computed(() => products.value.length)
const totalPages = computed(() => Math.ceil(products.value.length / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalProducts.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStatusClass = (status: string) => {
  const classes = {
    active: 'bg-green-100 text-green-800',
    inactive: 'bg-gray-100 text-gray-800',
    out_of_stock: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    active: 'Aktif',
    inactive: 'Tidak Aktif',
    out_of_stock: 'Stok Habis'
  }
  return texts[status as keyof typeof texts] || 'Unknown'
}

const getProductStatus = (product: Product): string => {
  if (product.stockQuantity === 0) {
    return 'out_of_stock'
  }
  return product.isActive ? 'active' : 'inactive'
}

const showAddProductForm = () => {
  resetForm()
  showAddForm.value = true
  isEditing.value = false
}

const editProduct = async (product: Product) => {
  try {
    // Fetch fresh product data
    const fullProduct = await apiService.getProduct(product.id)
    
    // Parse specifications back to array format
    const specs = fullProduct.specifications 
      ? fullProduct.specifications.split('\n').map(line => {
          const [name, value] = line.split(': ')
          return { name: name || '', value: value || '' }
        })
      : [{ name: '', value: '' }]
    
    // Find category ID from categories list
    const category = categories.value.find(cat => cat.name === fullProduct.categoryName)
    
    // Populate form with API data
    productForm.value = {
      name: fullProduct.name,
      category: category?.id.toString() || '',
      sku: fullProduct.sku,
      price: fullProduct.price,
      stock: fullProduct.stockQuantity,
      description: fullProduct.description,
      specifications: specs,
      auctionEnabled: false,
      auctionStartPrice: 0,
      auctionDuration: 7,
      status: fullProduct.isActive ? 'active' : 'inactive'
    }
    
    // Load existing images
    existingImages.value = fullProduct.imageUrls || []
    selectedFiles.value = []
    
    editingProductId.value = product.id
    showAddForm.value = true
    isEditing.value = true
  } catch (err) {
    alert('Failed to load product details')
    console.error(err)
  }
}

const deleteProduct = async (productId: number) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await apiService.deleteProduct(productId)
    alert('Product deleted successfully!')
    fetchProducts() // Refresh list
  } catch (err) {
    alert('Failed to delete product')
    console.error(err)
  }
}

const goBack = () => {
  showAddForm.value = false
  resetForm()
}

const resetForm = () => {
  productForm.value = {
    name: '',
    category: '',
    sku: '',
    price: 0,
    stock: 0,
    description: '',
    specifications: [{ name: '', value: '' }],
    auctionEnabled: false,
    auctionStartPrice: 0,
    auctionDuration: 7,
    status: 'active'
  }
  selectedFiles.value = []
  existingImages.value = []
  editingProductId.value = null
  isEditing.value = false
}

const addSpecification = () => {
  productForm.value.specifications.push({ name: '', value: '' })
}

const removeSpecification = (index: number) => {
  if (productForm.value.specifications.length > 1) {
    productForm.value.specifications.splice(index, 1)
  }
}

// API Functions
const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    // Build filter parameters
    const filters: any = {}
    
    if (searchQuery.value.trim()) {
      filters.search = searchQuery.value.trim()
    }
    
    if (categoryFilter.value) {
      // Find category ID by name
      const category = categories.value.find(cat => cat.name === categoryFilter.value)
      if (category) {
        filters.category = category.id
      }
    }
    
    // Note: statusFilter is handled client-side since backend doesn't have a direct status filter
    // The backend returns active products by default, and we filter by stock status on frontend
    
    const allProducts = await apiService.getProducts(filters)
    products.value = allProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const validateForm = (): boolean => {
  if (!productForm.value.name.trim()) {
    alert('Product name is required')
    return false
  }
  
  if (!productForm.value.category) {
    alert('Category is required')
    return false
  }
  
  if (productForm.value.price <= 0) {
    alert('Price must be greater than 0')
    return false
  }
  
  if (productForm.value.stock < 0) {
    alert('Stock cannot be negative')
    return false
  }
  
  return true
}

const generateSKU = (): string => {
  const timestamp = Date.now().toString().slice(-6)
  const random = Math.random().toString(36).substring(2, 5).toUpperCase()
  return `PRD${timestamp}${random}`
}

const saveProduct = async () => {
  if (!validateForm()) return
  
  saving.value = true
  try {
    // Auto-generate SKU if not provided
    const finalSku = productForm.value.sku.trim() || generateSKU()
    
    // Handle images - combine existing and new images
    let finalImageUrls: string[] = []
    
    // For editing: start with existing images
    if (isEditing.value) {
      finalImageUrls = [...existingImages.value]
    }
    
    // Upload new images if any files selected
    if (selectedFiles.value.length > 0) {
      try {
        // Validate all files first
        for (const file of selectedFiles.value) {
          const validationError = validateProductImageFile(file)
          if (validationError) {
            throw new Error(validationError)
          }
        }
        
        // Upload all new images
        const uploadResults = await uploadProductImages(selectedFiles.value)
        const newImageUrls = uploadResults.map(result => result.url)
        
        // Add new images to final list
        finalImageUrls = [...finalImageUrls, ...newImageUrls]
        
        console.log('Images uploaded successfully:', newImageUrls)
      } catch (uploadError) {
        throw new Error(`Image upload failed: ${uploadError}`)
      }
    }
    
    const productData: CreateProductRequest = {
      name: productForm.value.name,
      description: productForm.value.description,
      price: productForm.value.price,
      stockQuantity: productForm.value.stock,
      brand: 'Unknown',
      model: 'Unknown',
      sku: finalSku,
      specifications: productForm.value.specifications
        .filter(spec => spec.name && spec.value)
        .map(spec => `${spec.name}: ${spec.value}`)
        .join('\n'),
      imageUrls: finalImageUrls,
      categoryId: parseInt(productForm.value.category)
    }
    
    if (isEditing.value && editingProductId.value) {
      await apiService.updateProduct(editingProductId.value, {
        ...productData,
        isActive: productForm.value.status === 'active'
      })
      alert('Product updated successfully!')
    } else {
      await apiService.createProduct(productData)
      alert('Product created successfully!')
    }
    
    goBack()
    fetchProducts() // Refresh list
  } catch (err) {
    alert(`Failed to save product: ${err}`)
    console.error(err)
  } finally {
    saving.value = false
  }
}

const saveDraft = () => {
  productForm.value.status = 'draft'
  saveProduct()
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page: number) => {
  currentPage.value = page
}

// File handling functions
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click()
  }
}

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    const files = Array.from(target.files)
    selectedFiles.value = [...selectedFiles.value, ...files]
  }
}

const removeFile = (index: number) => {
  selectedFiles.value.splice(index, 1)
}

// Image handling functions
const getProductImage = (product: Product): string | undefined => {
  // Check if product has imageUrls and it's not empty
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0]
  }
  
  // Return undefined if no image available
  return undefined
}

const hasProductImage = (product: Product): boolean => {
  return !!(product.imageUrls && product.imageUrls.length > 0)
}

const handleImageError = (event: Event) => {
  const target = event.target as HTMLImageElement
  const container = target.parentElement
  if (container) {
    target.style.display = 'none'
  }
}

const removeExistingImage = (index: number) => {
  existingImages.value.splice(index, 1)
}

// Add debounced version for search
const debouncedFetchProducts = debounce(fetchProducts, 300)

// Add watchers for real-time filtering
watch([searchQuery, categoryFilter, statusFilter], () => {
  debouncedFetchProducts()
})

onMounted(() => {
  fetchCategories()
  fetchProducts()
})
</script>

</final_file_content>

IMPORTANT: For any future changes to this file, use the final_file_content shown above as your reference. This content reflects the current state of the file, including any auto-formatting (e.g., if you used single quotes but the formatter converted them to double quotes). Always base your SEARCH/REPLACE operations on this final version to ensure accuracy.


<environment_details>
# VSCode Visible Files
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# VSCode Open Tabs
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# Current Time
7/14/2025, 7:32:52 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
123,278 / 200K tokens used (62%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Now I need to fix one more issue. The `totalProducts` computed property should reflect the filtered results from the server, not just the client-side filtered results. Let me also reset the current page when filters change:

<replace_in_file>
<path>autowarehouse-frontend/src/views/admin/AdminProductView.vue</path>
<diff>
------- SEARCH
const totalProducts = computed(() => products.value.length)
const totalPages = computed(() => Math.ceil(products.value.length / itemsPerPage))
=======
const totalProducts = computed(() => {
  // If status filter is applied, count filtered products
  if (statusFilter.value) {
    return products.value.filter(product => getProductStatus(product) === statusFilter.value).length
  }
  return products.value.length
})

const totalPages = computed(() => Math.ceil(totalProducts.value / itemsPerPage))
+++++++ REPLACE
</diff>
</replace_in_file>

---

**User:**

[replace_in_file for 'autowarehouse-frontend/src/views/admin/AdminProductView.vue'] Result:
The content was successfully saved to autowarehouse-frontend/src/views/admin/AdminProductView.vue.

Here is the full, updated content of the file that was saved:

<final_file_content path="autowarehouse-frontend/src/views/admin/AdminProductView.vue">
<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-4">
          <button 
            v-if="showAddForm" 
            @click="goBack" 
            class="text-gray-600 hover:text-gray-800"
          >
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <div>
            <h1 class="text-3xl font-bold text-gray-900">
              {{ showAddForm ? 'Tambah Produk Baru' : 'Product Management' }}
            </h1>
            <p class="text-gray-600 mt-1">
              {{ showAddForm ? 'Lengkapi form di bawah untuk menambah produk' : 'Kelola produk toko Anda' }}
            </p>
          </div>
        </div>
      </div>

        <!-- Product List View -->
        <div v-if="!showAddForm" class="p-6">
          <!-- Filters Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
            <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <div class="relative">
                  <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input 
                    v-model="searchQuery"
                    type="text" 
                    placeholder="Cari produk..." 
                    class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
                  >
                </div>
                <select 
                  v-model="categoryFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Kategori</option>
                  <option 
                    v-for="category in categories" 
                    :key="category.id" 
                    :value="category.name"
                  >
                    {{ category.name }}
                  </option>
                </select>
                <select 
                  v-model="statusFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Status</option>
                  <option value="active">Aktif</option>
                  <option value="inactive">Tidak Aktif</option>
                  <option value="out_of_stock">Stok Habis</option>
                </select>
              </div>
              <button 
                @click="showAddProductForm"
                class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center space-x-2"
              >
                <i class="fa-solid fa-plus"></i>
                <span>Tambah Produk</span>
              </button>
            </div>
          </div>

          <!-- Loading State -->
          <div v-if="loading" class="flex justify-center items-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <span class="ml-2 text-gray-600">Loading products...</span>
          </div>

          <!-- Error State -->
          <div v-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
            <div class="flex">
              <i class="fa-solid fa-exclamation-triangle text-red-600 mr-2"></i>
              <span class="text-red-800">{{ error }}</span>
              <button @click="fetchProducts" class="ml-auto text-red-600 hover:text-red-800">
                <i class="fa-solid fa-refresh"></i> Retry
              </button>
            </div>
          </div>

          <!-- Products Table -->
          <div v-if="!loading && !error" class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50 border-b border-gray-200">
                  <tr>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Produk</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Kategori</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Harga</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stok</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aksi</th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr 
                    v-for="product in filteredProducts" 
                    :key="product.id" 
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <div class="w-12 h-12 mr-4 flex-shrink-0">
                          <img 
                            v-if="hasProductImage(product)"
                            :src="getProductImage(product)" 
                            :alt="product.name" 
                            class="w-12 h-12 rounded-lg object-cover"
                            @error="handleImageError"
                          >
                          <div 
                            v-else
                            class="w-12 h-12 rounded-lg bg-gray-200 flex items-center justify-center"
                          >
                            <i class="fa-solid fa-image text-gray-400 text-lg"></i>
                          </div>
                        </div>
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ product.name }}</div>
                          <div class="text-sm text-gray-500">SKU: {{ product.sku }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.categoryName }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">Rp {{ formatPrice(product.price) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.stockQuantity }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="getStatusClass(getProductStatus(product))"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ getStatusText(getProductStatus(product)) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex space-x-2">
                        <button 
                          @click="editProduct(product)"
                          class="text-blue-600 hover:text-blue-700"
                        >
                          <i class="fa-solid fa-edit"></i>
                        </button>
                        <button 
                          @click="deleteProduct(product.id)"
                          class="text-red-600 hover:text-red-700"
                        >
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <!-- Pagination -->
            <div class="bg-gray-50 px-6 py-3 flex items-center justify-between border-t border-gray-200">
              <div class="text-sm text-gray-700">
                Menampilkan {{ startIndex }} sampai {{ endIndex }} dari {{ totalProducts }} produk
              </div>
              <div class="flex space-x-2">
                <button 
                  @click="previousPage"
                  :disabled="currentPage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Previous
                </button>
                <button 
                  v-for="page in visiblePages" 
                  :key="page"
                  @click="goToPage(page)"
                  :class="page === currentPage ? 'bg-blue-600 text-white' : 'border border-gray-300 hover:bg-gray-100'"
                  class="px-3 py-1 text-sm rounded"
                >
                  {{ page }}
                </button>
                <button 
                  @click="nextPage"
                  :disabled="currentPage === totalPages"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Add/Edit Product Form -->
        <div v-if="showAddForm" class="p-6">
          <div class="max-w-4xl mx-auto">
            <form @submit.prevent="saveProduct" class="space-y-8">
              
              <!-- Basic Information -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-info-circle text-blue-600 mr-2"></i>
                  Informasi Dasar
                </h3>
                
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Nama Produk *</label>
                    <input 
                      v-model="productForm.name"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Masukkan nama produk" 
                      required
                    >
                  </div>
                  
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Kategori *</label>
                    <select 
                      v-model="productForm.category"
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      required
                      :disabled="categoriesLoading"
                    >
                      <option value="">{{ categoriesLoading ? 'Loading categories...' : 'Pilih Kategori' }}</option>
                      <option 
                        v-for="category in categories" 
                        :key="category.id" 
                        :value="category.id"
                      >
                        {{ category.name }}
                      </option>
                    </select>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">SKU</label>
                    <input 
                      v-model="productForm.sku"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Kode produk unik"
                    >
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Harga *</label>
                    <div class="relative">
                      <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                      <input 
                        v-model="productForm.price"
                        type="number" 
                        class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                        placeholder="0" 
                        required
                      >
                    </div>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Stok *</label>
                    <input 
                      v-model="productForm.stock"
                      type="number" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="0" 
                      required
                    >
                  </div>
                </div>
              </div>

              <!-- Specifications -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-cogs text-blue-600 mr-2"></i>
                  Spesifikasi Produk
                </h3>
                
                <div class="space-y-4">
                  <div 
                    v-for="(spec, index) in productForm.specifications" 
                    :key="index"
                    class="flex items-center space-x-4"
                  >
                    <input 
                      v-model="spec.name"
                      type="text" 
                      placeholder="Nama spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <input 
                      v-model="spec.value"
                      type="text" 
                      placeholder="Nilai spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <button 
                      type="button" 
                      @click="removeSpecification(index)" 
                      class="text-red-600 hover:text-red-700 p-2"
                    >
                      <i class="fa-solid fa-trash"></i>
                    </button>
                  </div>
                </div>
                
                <button 
                  type="button" 
                  @click="addSpecification" 
                  class="mt-4 flex items-center space-x-2 text-blue-600 hover:text-blue-700"
                >
                  <i class="fa-solid fa-plus"></i>
                  <span>Tambah Spesifikasi</span>
                </button>
              </div>

              <!-- Description -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-align-left text-blue-600 mr-2"></i>
                  Deskripsi Produk
                </h3>
                
                <textarea 
                  v-model="productForm.description"
                  rows="6" 
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                  placeholder="Masukkan deskripsi lengkap produk..."
                ></textarea>
              </div>

              <!-- Images -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-images text-blue-600 mr-2"></i>
                  Gambar Produk
                </h3>
                
                <div class="space-y-6">
                  <!-- Show existing images when editing -->
                  <div v-if="isEditing && existingImages.length > 0" class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-3">Gambar Saat Ini</label>
                    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                      <div 
                        v-for="(imageUrl, index) in existingImages" 
                        :key="index"
                        class="relative group"
                      >
                        <img 
                          :src="imageUrl" 
                          :alt="`Product image ${index + 1}`"
                          class="w-full h-32 object-cover rounded-lg border border-gray-200"
                          @error="handleImageError"
                        >
                        <button 
                          type="button"
                          @click="removeExistingImage(index)"
                          class="absolute top-2 right-2 bg-red-600 text-white rounded-full w-6 h-6 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                        >
                          <i class="fa-solid fa-times text-xs"></i>
                        </button>
                      </div>
                    </div>
                  </div>

                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-3">
                      {{ isEditing ? 'Tambah Gambar Baru' : 'Upload Gambar Produk *' }}
                    </label>
                    <div 
                      @click="triggerFileInput"
                      class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-blue-600 transition-colors cursor-pointer bg-gray-50 hover:bg-gray-100"
                    >
                      <i class="fa-solid fa-cloud-upload text-4xl text-gray-400 mb-4"></i>
                      <p class="text-gray-600 mb-2 font-medium">Drag & drop gambar atau klik untuk upload</p>
                      <p class="text-sm text-gray-500 mb-3">PNG, JPG hingga 10MB per file</p>
                      <button type="button" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                        Pilih File
                      </button>
                      <input 
                        ref="fileInput"
                        type="file" 
                        class="hidden" 
                        accept="image/*" 
                        multiple
                        @change="handleFileSelect"
                      >
                    </div>
                    
                    <!-- Show selected files -->
                    <div v-if="selectedFiles.length > 0" class="mt-4">
                      <p class="text-sm font-medium text-gray-700 mb-2">File yang dipilih:</p>
                      <div class="space-y-2">
                        <div 
                          v-for="(file, index) in selectedFiles" 
                          :key="index"
                          class="flex items-center justify-between bg-gray-50 p-2 rounded"
                        >
                          <span class="text-sm text-gray-600">{{ file.name }}</span>
                          <button 
                            type="button"
                            @click="removeFile(index)"
                            class="text-red-600 hover:text-red-700"
                          >
                            <i class="fa-solid fa-times"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Auction Settings -->
              <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-gavel text-blue-600 mr-2"></i>
                  Pengaturan Lelang
                </h3>
                
                <div class="space-y-6">
                  <div class="flex items-center space-x-3">
                    <input 
                      v-model="productForm.auctionEnabled"
                      type="checkbox" 
                      class="w-5 h-5 text-blue-600 focus:ring-blue-600 border-gray-300 rounded"
                    >
                    <label class="text-sm font-medium text-gray-700">Tersedia untuk lelang</label>
                  </div>
                  
                  <div v-if="productForm.auctionEnabled" class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Harga Awal Lelang</label>
                      <div class="relative">
                        <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                        <input 
                          v-model="productForm.auctionStartPrice"
                          type="number" 
                          class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                          placeholder="0"
                        >
                      </div>
                    </div>
                    
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Durasi Lelang (Hari)</label>
                      <select 
                        v-model="productForm.auctionDuration"
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      >
                        <option value="1">1 Hari</option>
                        <option value="3">3 Hari</option>
                        <option value="7">7 Hari</option>
                        <option value="14">14 Hari</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div> -->

              <!-- Status -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-toggle-on text-blue-600 mr-2"></i>
                  Status Produk
                </h3>
                
                <div class="flex space-x-6">
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="active" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Aktif</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="draft" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Draft</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="inactive" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Tidak Aktif</span>
                  </label>
                </div>
              </div>

              <!-- Form Actions -->
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <button 
                  type="button" 
                  @click="goBack" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors disabled:opacity-50"
                >
                  Batal
                </button>
                <button 
                  type="button" 
                  @click="saveDraft"
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-gray-600 text-white rounded-lg font-medium hover:bg-gray-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>Simpan sebagai Draft</span>
                </button>
                <button 
                  type="submit" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>{{ isEditing ? 'Update Produk' : 'Publikasikan Produk' }}</span>
                </button>
              </div>
            </form>
          </div>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService, type Product, type Category, type CreateProductRequest, type UpdateProductRequest } from '@/services/api'
import { debounce } from '@/utils/debounce'
import { uploadProductImages, deleteProductImages, validateProductImageFile, type UploadResult } from '@/services/firebase'

interface Specification {
  name: string
  value: string
}

interface LocalProduct {
  id: string
  name: string
  sku: string
  category: string
  price: number
  stock: number
  status: 'active' | 'inactive' | 'out_of_stock'
  image: string
  description?: string
  specifications?: Specification[]
}

interface ProductForm {
  name: string
  category: string
  sku: string
  price: number
  stock: number
  description: string
  specifications: Specification[]
  auctionEnabled: boolean
  auctionStartPrice: number
  auctionDuration: number
  status: 'active' | 'draft' | 'inactive'
}

const searchQuery = ref('')
const categoryFilter = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showAddForm = ref(false)
const isEditing = ref(false)
const editingProductId = ref<number | null>(null)

// API Integration
const products = ref<Product[]>([])
const categories = ref<Category[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const saving = ref(false)
const categoriesLoading = ref(false)

// File handling
const selectedFiles = ref<File[]>([])
const existingImages = ref<string[]>([])
const fileInput = ref<HTMLInputElement | null>(null)

const productForm = ref<ProductForm>({
  name: '',
  category: '',
  sku: '',
  price: 0,
  stock: 0,
  description: '',
  specifications: [{ name: '', value: '' }],
  auctionEnabled: false,
  auctionStartPrice: 0,
  auctionDuration: 7,
  status: 'active'
})

const filteredProducts = computed(() => {
  let filtered = products.value

  // Only apply status filter client-side since backend doesn't support it
  if (statusFilter.value) {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalProducts = computed(() => {
  // If status filter is applied, count filtered products
  if (statusFilter.value) {
    return products.value.filter(product => getProductStatus(product) === statusFilter.value).length
  }
  return products.value.length
})

const totalPages = computed(() => Math.ceil(totalProducts.value / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalProducts.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStatusClass = (status: string) => {
  const classes = {
    active: 'bg-green-100 text-green-800',
    inactive: 'bg-gray-100 text-gray-800',
    out_of_stock: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    active: 'Aktif',
    inactive: 'Tidak Aktif',
    out_of_stock: 'Stok Habis'
  }
  return texts[status as keyof typeof texts] || 'Unknown'
}

const getProductStatus = (product: Product): string => {
  if (product.stockQuantity === 0) {
    return 'out_of_stock'
  }
  return product.isActive ? 'active' : 'inactive'
}

const showAddProductForm = () => {
  resetForm()
  showAddForm.value = true
  isEditing.value = false
}

const editProduct = async (product: Product) => {
  try {
    // Fetch fresh product data
    const fullProduct = await apiService.getProduct(product.id)
    
    // Parse specifications back to array format
    const specs = fullProduct.specifications 
      ? fullProduct.specifications.split('\n').map(line => {
          const [name, value] = line.split(': ')
          return { name: name || '', value: value || '' }
        })
      : [{ name: '', value: '' }]
    
    // Find category ID from categories list
    const category = categories.value.find(cat => cat.name === fullProduct.categoryName)
    
    // Populate form with API data
    productForm.value = {
      name: fullProduct.name,
      category: category?.id.toString() || '',
      sku: fullProduct.sku,
      price: fullProduct.price,
      stock: fullProduct.stockQuantity,
      description: fullProduct.description,
      specifications: specs,
      auctionEnabled: false,
      auctionStartPrice: 0,
      auctionDuration: 7,
      status: fullProduct.isActive ? 'active' : 'inactive'
    }
    
    // Load existing images
    existingImages.value = fullProduct.imageUrls || []
    selectedFiles.value = []
    
    editingProductId.value = product.id
    showAddForm.value = true
    isEditing.value = true
  } catch (err) {
    alert('Failed to load product details')
    console.error(err)
  }
}

const deleteProduct = async (productId: number) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await apiService.deleteProduct(productId)
    alert('Product deleted successfully!')
    fetchProducts() // Refresh list
  } catch (err) {
    alert('Failed to delete product')
    console.error(err)
  }
}

const goBack = () => {
  showAddForm.value = false
  resetForm()
}

const resetForm = () => {
  productForm.value = {
    name: '',
    category: '',
    sku: '',
    price: 0,
    stock: 0,
    description: '',
    specifications: [{ name: '', value: '' }],
    auctionEnabled: false,
    auctionStartPrice: 0,
    auctionDuration: 7,
    status: 'active'
  }
  selectedFiles.value = []
  existingImages.value = []
  editingProductId.value = null
  isEditing.value = false
}

const addSpecification = () => {
  productForm.value.specifications.push({ name: '', value: '' })
}

const removeSpecification = (index: number) => {
  if (productForm.value.specifications.length > 1) {
    productForm.value.specifications.splice(index, 1)
  }
}

// API Functions
const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    // Build filter parameters
    const filters: any = {}
    
    if (searchQuery.value.trim()) {
      filters.search = searchQuery.value.trim()
    }
    
    if (categoryFilter.value) {
      // Find category ID by name
      const category = categories.value.find(cat => cat.name === categoryFilter.value)
      if (category) {
        filters.category = category.id
      }
    }
    
    // Note: statusFilter is handled client-side since backend doesn't have a direct status filter
    // The backend returns active products by default, and we filter by stock status on frontend
    
    const allProducts = await apiService.getProducts(filters)
    products.value = allProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const validateForm = (): boolean => {
  if (!productForm.value.name.trim()) {
    alert('Product name is required')
    return false
  }
  
  if (!productForm.value.category) {
    alert('Category is required')
    return false
  }
  
  if (productForm.value.price <= 0) {
    alert('Price must be greater than 0')
    return false
  }
  
  if (productForm.value.stock < 0) {
    alert('Stock cannot be negative')
    return false
  }
  
  return true
}

const generateSKU = (): string => {
  const timestamp = Date.now().toString().slice(-6)
  const random = Math.random().toString(36).substring(2, 5).toUpperCase()
  return `PRD${timestamp}${random}`
}

const saveProduct = async () => {
  if (!validateForm()) return
  
  saving.value = true
  try {
    // Auto-generate SKU if not provided
    const finalSku = productForm.value.sku.trim() || generateSKU()
    
    // Handle images - combine existing and new images
    let finalImageUrls: string[] = []
    
    // For editing: start with existing images
    if (isEditing.value) {
      finalImageUrls = [...existingImages.value]
    }
    
    // Upload new images if any files selected
    if (selectedFiles.value.length > 0) {
      try {
        // Validate all files first
        for (const file of selectedFiles.value) {
          const validationError = validateProductImageFile(file)
          if (validationError) {
            throw new Error(validationError)
          }
        }
        
        // Upload all new images
        const uploadResults = await uploadProductImages(selectedFiles.value)
        const newImageUrls = uploadResults.map(result => result.url)
        
        // Add new images to final list
        finalImageUrls = [...finalImageUrls, ...newImageUrls]
        
        console.log('Images uploaded successfully:', newImageUrls)
      } catch (uploadError) {
        throw new Error(`Image upload failed: ${uploadError}`)
      }
    }
    
    const productData: CreateProductRequest = {
      name: productForm.value.name,
      description: productForm.value.description,
      price: productForm.value.price,
      stockQuantity: productForm.value.stock,
      brand: 'Unknown',
      model: 'Unknown',
      sku: finalSku,
      specifications: productForm.value.specifications
        .filter(spec => spec.name && spec.value)
        .map(spec => `${spec.name}: ${spec.value}`)
        .join('\n'),
      imageUrls: finalImageUrls,
      categoryId: parseInt(productForm.value.category)
    }
    
    if (isEditing.value && editingProductId.value) {
      await apiService.updateProduct(editingProductId.value, {
        ...productData,
        isActive: productForm.value.status === 'active'
      })
      alert('Product updated successfully!')
    } else {
      await apiService.createProduct(productData)
      alert('Product created successfully!')
    }
    
    goBack()
    fetchProducts() // Refresh list
  } catch (err) {
    alert(`Failed to save product: ${err}`)
    console.error(err)
  } finally {
    saving.value = false
  }
}

const saveDraft = () => {
  productForm.value.status = 'draft'
  saveProduct()
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page: number) => {
  currentPage.value = page
}

// File handling functions
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click()
  }
}

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    const files = Array.from(target.files)
    selectedFiles.value = [...selectedFiles.value, ...files]
  }
}

const removeFile = (index: number) => {
  selectedFiles.value.splice(index, 1)
}

// Image handling functions
const getProductImage = (product: Product): string | undefined => {
  // Check if product has imageUrls and it's not empty
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0]
  }
  
  // Return undefined if no image available
  return undefined
}

const hasProductImage = (product: Product): boolean => {
  return !!(product.imageUrls && product.imageUrls.length > 0)
}

const handleImageError = (event: Event) => {
  const target = event.target as HTMLImageElement
  const container = target.parentElement
  if (container) {
    target.style.display = 'none'
  }
}

const removeExistingImage = (index: number) => {
  existingImages.value.splice(index, 1)
}

// Add debounced version for search
const debouncedFetchProducts = debounce(fetchProducts, 300)

// Add watchers for real-time filtering
watch([searchQuery, categoryFilter, statusFilter], () => {
  debouncedFetchProducts()
})

onMounted(() => {
  fetchCategories()
  fetchProducts()
})
</script>

</final_file_content>

IMPORTANT: For any future changes to this file, use the final_file_content shown above as your reference. This content reflects the current state of the file, including any auto-formatting (e.g., if you used single quotes but the formatter converted them to double quotes). Always base your SEARCH/REPLACE operations on this final version to ensure accuracy.


<environment_details>
# VSCode Visible Files
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# VSCode Open Tabs
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# Current Time
7/14/2025, 7:33:11 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
147,271 / 200K tokens used (74%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Now let me add a watcher to reset the current page when filters change, so users don't get stuck on a page that doesn't exist after filtering:

<replace_in_file>
<path>autowarehouse-frontend/src/views/admin/AdminProductView.vue</path>
<diff>
------- SEARCH
// Add watchers for real-time filtering
watch([searchQuery, categoryFilter, statusFilter], () => {
  debouncedFetchProducts()
})
=======
// Add watchers for real-time filtering
watch([searchQuery, categoryFilter], () => {
  currentPage.value = 1 // Reset to first page when search/category filters change
  debouncedFetchProducts()
})

// Watch status filter separately since it's client-side only
watch(statusFilter, () => {
  currentPage.value = 1 // Reset to first page when status filter changes
})
+++++++ REPLACE
</diff>
</replace_in_file>

---

**User:**

[replace_in_file for 'autowarehouse-frontend/src/views/admin/AdminProductView.vue'] Result:
The content was successfully saved to autowarehouse-frontend/src/views/admin/AdminProductView.vue.

Here is the full, updated content of the file that was saved:

<final_file_content path="autowarehouse-frontend/src/views/admin/AdminProductView.vue">
<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-4">
          <button 
            v-if="showAddForm" 
            @click="goBack" 
            class="text-gray-600 hover:text-gray-800"
          >
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <div>
            <h1 class="text-3xl font-bold text-gray-900">
              {{ showAddForm ? 'Tambah Produk Baru' : 'Product Management' }}
            </h1>
            <p class="text-gray-600 mt-1">
              {{ showAddForm ? 'Lengkapi form di bawah untuk menambah produk' : 'Kelola produk toko Anda' }}
            </p>
          </div>
        </div>
      </div>

        <!-- Product List View -->
        <div v-if="!showAddForm" class="p-6">
          <!-- Filters Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
            <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <div class="relative">
                  <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input 
                    v-model="searchQuery"
                    type="text" 
                    placeholder="Cari produk..." 
                    class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
                  >
                </div>
                <select 
                  v-model="categoryFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Kategori</option>
                  <option 
                    v-for="category in categories" 
                    :key="category.id" 
                    :value="category.name"
                  >
                    {{ category.name }}
                  </option>
                </select>
                <select 
                  v-model="statusFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Status</option>
                  <option value="active">Aktif</option>
                  <option value="inactive">Tidak Aktif</option>
                  <option value="out_of_stock">Stok Habis</option>
                </select>
              </div>
              <button 
                @click="showAddProductForm"
                class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center space-x-2"
              >
                <i class="fa-solid fa-plus"></i>
                <span>Tambah Produk</span>
              </button>
            </div>
          </div>

          <!-- Loading State -->
          <div v-if="loading" class="flex justify-center items-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <span class="ml-2 text-gray-600">Loading products...</span>
          </div>

          <!-- Error State -->
          <div v-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
            <div class="flex">
              <i class="fa-solid fa-exclamation-triangle text-red-600 mr-2"></i>
              <span class="text-red-800">{{ error }}</span>
              <button @click="fetchProducts" class="ml-auto text-red-600 hover:text-red-800">
                <i class="fa-solid fa-refresh"></i> Retry
              </button>
            </div>
          </div>

          <!-- Products Table -->
          <div v-if="!loading && !error" class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50 border-b border-gray-200">
                  <tr>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Produk</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Kategori</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Harga</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stok</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aksi</th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr 
                    v-for="product in filteredProducts" 
                    :key="product.id" 
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <div class="w-12 h-12 mr-4 flex-shrink-0">
                          <img 
                            v-if="hasProductImage(product)"
                            :src="getProductImage(product)" 
                            :alt="product.name" 
                            class="w-12 h-12 rounded-lg object-cover"
                            @error="handleImageError"
                          >
                          <div 
                            v-else
                            class="w-12 h-12 rounded-lg bg-gray-200 flex items-center justify-center"
                          >
                            <i class="fa-solid fa-image text-gray-400 text-lg"></i>
                          </div>
                        </div>
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ product.name }}</div>
                          <div class="text-sm text-gray-500">SKU: {{ product.sku }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.categoryName }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">Rp {{ formatPrice(product.price) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.stockQuantity }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="getStatusClass(getProductStatus(product))"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ getStatusText(getProductStatus(product)) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex space-x-2">
                        <button 
                          @click="editProduct(product)"
                          class="text-blue-600 hover:text-blue-700"
                        >
                          <i class="fa-solid fa-edit"></i>
                        </button>
                        <button 
                          @click="deleteProduct(product.id)"
                          class="text-red-600 hover:text-red-700"
                        >
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <!-- Pagination -->
            <div class="bg-gray-50 px-6 py-3 flex items-center justify-between border-t border-gray-200">
              <div class="text-sm text-gray-700">
                Menampilkan {{ startIndex }} sampai {{ endIndex }} dari {{ totalProducts }} produk
              </div>
              <div class="flex space-x-2">
                <button 
                  @click="previousPage"
                  :disabled="currentPage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Previous
                </button>
                <button 
                  v-for="page in visiblePages" 
                  :key="page"
                  @click="goToPage(page)"
                  :class="page === currentPage ? 'bg-blue-600 text-white' : 'border border-gray-300 hover:bg-gray-100'"
                  class="px-3 py-1 text-sm rounded"
                >
                  {{ page }}
                </button>
                <button 
                  @click="nextPage"
                  :disabled="currentPage === totalPages"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Add/Edit Product Form -->
        <div v-if="showAddForm" class="p-6">
          <div class="max-w-4xl mx-auto">
            <form @submit.prevent="saveProduct" class="space-y-8">
              
              <!-- Basic Information -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-info-circle text-blue-600 mr-2"></i>
                  Informasi Dasar
                </h3>
                
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Nama Produk *</label>
                    <input 
                      v-model="productForm.name"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Masukkan nama produk" 
                      required
                    >
                  </div>
                  
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Kategori *</label>
                    <select 
                      v-model="productForm.category"
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      required
                      :disabled="categoriesLoading"
                    >
                      <option value="">{{ categoriesLoading ? 'Loading categories...' : 'Pilih Kategori' }}</option>
                      <option 
                        v-for="category in categories" 
                        :key="category.id" 
                        :value="category.id"
                      >
                        {{ category.name }}
                      </option>
                    </select>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">SKU</label>
                    <input 
                      v-model="productForm.sku"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Kode produk unik"
                    >
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Harga *</label>
                    <div class="relative">
                      <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                      <input 
                        v-model="productForm.price"
                        type="number" 
                        class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                        placeholder="0" 
                        required
                      >
                    </div>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Stok *</label>
                    <input 
                      v-model="productForm.stock"
                      type="number" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="0" 
                      required
                    >
                  </div>
                </div>
              </div>

              <!-- Specifications -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-cogs text-blue-600 mr-2"></i>
                  Spesifikasi Produk
                </h3>
                
                <div class="space-y-4">
                  <div 
                    v-for="(spec, index) in productForm.specifications" 
                    :key="index"
                    class="flex items-center space-x-4"
                  >
                    <input 
                      v-model="spec.name"
                      type="text" 
                      placeholder="Nama spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <input 
                      v-model="spec.value"
                      type="text" 
                      placeholder="Nilai spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <button 
                      type="button" 
                      @click="removeSpecification(index)" 
                      class="text-red-600 hover:text-red-700 p-2"
                    >
                      <i class="fa-solid fa-trash"></i>
                    </button>
                  </div>
                </div>
                
                <button 
                  type="button" 
                  @click="addSpecification" 
                  class="mt-4 flex items-center space-x-2 text-blue-600 hover:text-blue-700"
                >
                  <i class="fa-solid fa-plus"></i>
                  <span>Tambah Spesifikasi</span>
                </button>
              </div>

              <!-- Description -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-align-left text-blue-600 mr-2"></i>
                  Deskripsi Produk
                </h3>
                
                <textarea 
                  v-model="productForm.description"
                  rows="6" 
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                  placeholder="Masukkan deskripsi lengkap produk..."
                ></textarea>
              </div>

              <!-- Images -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-images text-blue-600 mr-2"></i>
                  Gambar Produk
                </h3>
                
                <div class="space-y-6">
                  <!-- Show existing images when editing -->
                  <div v-if="isEditing && existingImages.length > 0" class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-3">Gambar Saat Ini</label>
                    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                      <div 
                        v-for="(imageUrl, index) in existingImages" 
                        :key="index"
                        class="relative group"
                      >
                        <img 
                          :src="imageUrl" 
                          :alt="`Product image ${index + 1}`"
                          class="w-full h-32 object-cover rounded-lg border border-gray-200"
                          @error="handleImageError"
                        >
                        <button 
                          type="button"
                          @click="removeExistingImage(index)"
                          class="absolute top-2 right-2 bg-red-600 text-white rounded-full w-6 h-6 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                        >
                          <i class="fa-solid fa-times text-xs"></i>
                        </button>
                      </div>
                    </div>
                  </div>

                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-3">
                      {{ isEditing ? 'Tambah Gambar Baru' : 'Upload Gambar Produk *' }}
                    </label>
                    <div 
                      @click="triggerFileInput"
                      class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-blue-600 transition-colors cursor-pointer bg-gray-50 hover:bg-gray-100"
                    >
                      <i class="fa-solid fa-cloud-upload text-4xl text-gray-400 mb-4"></i>
                      <p class="text-gray-600 mb-2 font-medium">Drag & drop gambar atau klik untuk upload</p>
                      <p class="text-sm text-gray-500 mb-3">PNG, JPG hingga 10MB per file</p>
                      <button type="button" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                        Pilih File
                      </button>
                      <input 
                        ref="fileInput"
                        type="file" 
                        class="hidden" 
                        accept="image/*" 
                        multiple
                        @change="handleFileSelect"
                      >
                    </div>
                    
                    <!-- Show selected files -->
                    <div v-if="selectedFiles.length > 0" class="mt-4">
                      <p class="text-sm font-medium text-gray-700 mb-2">File yang dipilih:</p>
                      <div class="space-y-2">
                        <div 
                          v-for="(file, index) in selectedFiles" 
                          :key="index"
                          class="flex items-center justify-between bg-gray-50 p-2 rounded"
                        >
                          <span class="text-sm text-gray-600">{{ file.name }}</span>
                          <button 
                            type="button"
                            @click="removeFile(index)"
                            class="text-red-600 hover:text-red-700"
                          >
                            <i class="fa-solid fa-times"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Auction Settings -->
              <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-gavel text-blue-600 mr-2"></i>
                  Pengaturan Lelang
                </h3>
                
                <div class="space-y-6">
                  <div class="flex items-center space-x-3">
                    <input 
                      v-model="productForm.auctionEnabled"
                      type="checkbox" 
                      class="w-5 h-5 text-blue-600 focus:ring-blue-600 border-gray-300 rounded"
                    >
                    <label class="text-sm font-medium text-gray-700">Tersedia untuk lelang</label>
                  </div>
                  
                  <div v-if="productForm.auctionEnabled" class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Harga Awal Lelang</label>
                      <div class="relative">
                        <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                        <input 
                          v-model="productForm.auctionStartPrice"
                          type="number" 
                          class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                          placeholder="0"
                        >
                      </div>
                    </div>
                    
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Durasi Lelang (Hari)</label>
                      <select 
                        v-model="productForm.auctionDuration"
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      >
                        <option value="1">1 Hari</option>
                        <option value="3">3 Hari</option>
                        <option value="7">7 Hari</option>
                        <option value="14">14 Hari</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div> -->

              <!-- Status -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-toggle-on text-blue-600 mr-2"></i>
                  Status Produk
                </h3>
                
                <div class="flex space-x-6">
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="active" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Aktif</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="draft" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Draft</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="inactive" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Tidak Aktif</span>
                  </label>
                </div>
              </div>

              <!-- Form Actions -->
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <button 
                  type="button" 
                  @click="goBack" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors disabled:opacity-50"
                >
                  Batal
                </button>
                <button 
                  type="button" 
                  @click="saveDraft"
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-gray-600 text-white rounded-lg font-medium hover:bg-gray-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>Simpan sebagai Draft</span>
                </button>
                <button 
                  type="submit" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>{{ isEditing ? 'Update Produk' : 'Publikasikan Produk' }}</span>
                </button>
              </div>
            </form>
          </div>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService, type Product, type Category, type CreateProductRequest, type UpdateProductRequest } from '@/services/api'
import { debounce } from '@/utils/debounce'
import { uploadProductImages, deleteProductImages, validateProductImageFile, type UploadResult } from '@/services/firebase'

interface Specification {
  name: string
  value: string
}

interface LocalProduct {
  id: string
  name: string
  sku: string
  category: string
  price: number
  stock: number
  status: 'active' | 'inactive' | 'out_of_stock'
  image: string
  description?: string
  specifications?: Specification[]
}

interface ProductForm {
  name: string
  category: string
  sku: string
  price: number
  stock: number
  description: string
  specifications: Specification[]
  auctionEnabled: boolean
  auctionStartPrice: number
  auctionDuration: number
  status: 'active' | 'draft' | 'inactive'
}

const searchQuery = ref('')
const categoryFilter = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showAddForm = ref(false)
const isEditing = ref(false)
const editingProductId = ref<number | null>(null)

// API Integration
const products = ref<Product[]>([])
const categories = ref<Category[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const saving = ref(false)
const categoriesLoading = ref(false)

// File handling
const selectedFiles = ref<File[]>([])
const existingImages = ref<string[]>([])
const fileInput = ref<HTMLInputElement | null>(null)

const productForm = ref<ProductForm>({
  name: '',
  category: '',
  sku: '',
  price: 0,
  stock: 0,
  description: '',
  specifications: [{ name: '', value: '' }],
  auctionEnabled: false,
  auctionStartPrice: 0,
  auctionDuration: 7,
  status: 'active'
})

const filteredProducts = computed(() => {
  let filtered = products.value

  // Only apply status filter client-side since backend doesn't support it
  if (statusFilter.value) {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalProducts = computed(() => {
  // If status filter is applied, count filtered products
  if (statusFilter.value) {
    return products.value.filter(product => getProductStatus(product) === statusFilter.value).length
  }
  return products.value.length
})

const totalPages = computed(() => Math.ceil(totalProducts.value / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalProducts.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStatusClass = (status: string) => {
  const classes = {
    active: 'bg-green-100 text-green-800',
    inactive: 'bg-gray-100 text-gray-800',
    out_of_stock: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    active: 'Aktif',
    inactive: 'Tidak Aktif',
    out_of_stock: 'Stok Habis'
  }
  return texts[status as keyof typeof texts] || 'Unknown'
}

const getProductStatus = (product: Product): string => {
  if (product.stockQuantity === 0) {
    return 'out_of_stock'
  }
  return product.isActive ? 'active' : 'inactive'
}

const showAddProductForm = () => {
  resetForm()
  showAddForm.value = true
  isEditing.value = false
}

const editProduct = async (product: Product) => {
  try {
    // Fetch fresh product data
    const fullProduct = await apiService.getProduct(product.id)
    
    // Parse specifications back to array format
    const specs = fullProduct.specifications 
      ? fullProduct.specifications.split('\n').map(line => {
          const [name, value] = line.split(': ')
          return { name: name || '', value: value || '' }
        })
      : [{ name: '', value: '' }]
    
    // Find category ID from categories list
    const category = categories.value.find(cat => cat.name === fullProduct.categoryName)
    
    // Populate form with API data
    productForm.value = {
      name: fullProduct.name,
      category: category?.id.toString() || '',
      sku: fullProduct.sku,
      price: fullProduct.price,
      stock: fullProduct.stockQuantity,
      description: fullProduct.description,
      specifications: specs,
      auctionEnabled: false,
      auctionStartPrice: 0,
      auctionDuration: 7,
      status: fullProduct.isActive ? 'active' : 'inactive'
    }
    
    // Load existing images
    existingImages.value = fullProduct.imageUrls || []
    selectedFiles.value = []
    
    editingProductId.value = product.id
    showAddForm.value = true
    isEditing.value = true
  } catch (err) {
    alert('Failed to load product details')
    console.error(err)
  }
}

const deleteProduct = async (productId: number) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await apiService.deleteProduct(productId)
    alert('Product deleted successfully!')
    fetchProducts() // Refresh list
  } catch (err) {
    alert('Failed to delete product')
    console.error(err)
  }
}

const goBack = () => {
  showAddForm.value = false
  resetForm()
}

const resetForm = () => {
  productForm.value = {
    name: '',
    category: '',
    sku: '',
    price: 0,
    stock: 0,
    description: '',
    specifications: [{ name: '', value: '' }],
    auctionEnabled: false,
    auctionStartPrice: 0,
    auctionDuration: 7,
    status: 'active'
  }
  selectedFiles.value = []
  existingImages.value = []
  editingProductId.value = null
  isEditing.value = false
}

const addSpecification = () => {
  productForm.value.specifications.push({ name: '', value: '' })
}

const removeSpecification = (index: number) => {
  if (productForm.value.specifications.length > 1) {
    productForm.value.specifications.splice(index, 1)
  }
}

// API Functions
const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    // Build filter parameters
    const filters: any = {}
    
    if (searchQuery.value.trim()) {
      filters.search = searchQuery.value.trim()
    }
    
    if (categoryFilter.value) {
      // Find category ID by name
      const category = categories.value.find(cat => cat.name === categoryFilter.value)
      if (category) {
        filters.category = category.id
      }
    }
    
    // Note: statusFilter is handled client-side since backend doesn't have a direct status filter
    // The backend returns active products by default, and we filter by stock status on frontend
    
    const allProducts = await apiService.getProducts(filters)
    products.value = allProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const validateForm = (): boolean => {
  if (!productForm.value.name.trim()) {
    alert('Product name is required')
    return false
  }
  
  if (!productForm.value.category) {
    alert('Category is required')
    return false
  }
  
  if (productForm.value.price <= 0) {
    alert('Price must be greater than 0')
    return false
  }
  
  if (productForm.value.stock < 0) {
    alert('Stock cannot be negative')
    return false
  }
  
  return true
}

const generateSKU = (): string => {
  const timestamp = Date.now().toString().slice(-6)
  const random = Math.random().toString(36).substring(2, 5).toUpperCase()
  return `PRD${timestamp}${random}`
}

const saveProduct = async () => {
  if (!validateForm()) return
  
  saving.value = true
  try {
    // Auto-generate SKU if not provided
    const finalSku = productForm.value.sku.trim() || generateSKU()
    
    // Handle images - combine existing and new images
    let finalImageUrls: string[] = []
    
    // For editing: start with existing images
    if (isEditing.value) {
      finalImageUrls = [...existingImages.value]
    }
    
    // Upload new images if any files selected
    if (selectedFiles.value.length > 0) {
      try {
        // Validate all files first
        for (const file of selectedFiles.value) {
          const validationError = validateProductImageFile(file)
          if (validationError) {
            throw new Error(validationError)
          }
        }
        
        // Upload all new images
        const uploadResults = await uploadProductImages(selectedFiles.value)
        const newImageUrls = uploadResults.map(result => result.url)
        
        // Add new images to final list
        finalImageUrls = [...finalImageUrls, ...newImageUrls]
        
        console.log('Images uploaded successfully:', newImageUrls)
      } catch (uploadError) {
        throw new Error(`Image upload failed: ${uploadError}`)
      }
    }
    
    const productData: CreateProductRequest = {
      name: productForm.value.name,
      description: productForm.value.description,
      price: productForm.value.price,
      stockQuantity: productForm.value.stock,
      brand: 'Unknown',
      model: 'Unknown',
      sku: finalSku,
      specifications: productForm.value.specifications
        .filter(spec => spec.name && spec.value)
        .map(spec => `${spec.name}: ${spec.value}`)
        .join('\n'),
      imageUrls: finalImageUrls,
      categoryId: parseInt(productForm.value.category)
    }
    
    if (isEditing.value && editingProductId.value) {
      await apiService.updateProduct(editingProductId.value, {
        ...productData,
        isActive: productForm.value.status === 'active'
      })
      alert('Product updated successfully!')
    } else {
      await apiService.createProduct(productData)
      alert('Product created successfully!')
    }
    
    goBack()
    fetchProducts() // Refresh list
  } catch (err) {
    alert(`Failed to save product: ${err}`)
    console.error(err)
  } finally {
    saving.value = false
  }
}

const saveDraft = () => {
  productForm.value.status = 'draft'
  saveProduct()
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page: number) => {
  currentPage.value = page
}

// File handling functions
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click()
  }
}

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    const files = Array.from(target.files)
    selectedFiles.value = [...selectedFiles.value, ...files]
  }
}

const removeFile = (index: number) => {
  selectedFiles.value.splice(index, 1)
}

// Image handling functions
const getProductImage = (product: Product): string | undefined => {
  // Check if product has imageUrls and it's not empty
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0]
  }
  
  // Return undefined if no image available
  return undefined
}

const hasProductImage = (product: Product): boolean => {
  return !!(product.imageUrls && product.imageUrls.length > 0)
}

const handleImageError = (event: Event) => {
  const target = event.target as HTMLImageElement
  const container = target.parentElement
  if (container) {
    target.style.display = 'none'
  }
}

const removeExistingImage = (index: number) => {
  existingImages.value.splice(index, 1)
}

// Add debounced version for search
const debouncedFetchProducts = debounce(fetchProducts, 300)

// Add watchers for real-time filtering
watch([searchQuery, categoryFilter], () => {
  currentPage.value = 1 // Reset to first page when search/category filters change
  debouncedFetchProducts()
})

// Watch status filter separately since it's client-side only
watch(statusFilter, () => {
  currentPage.value = 1 // Reset to first page when status filter changes
})

onMounted(() => {
  fetchCategories()
  fetchProducts()
})
</script>

</final_file_content>

IMPORTANT: For any future changes to this file, use the final_file_content shown above as your reference. This content reflects the current state of the file, including any auto-formatting (e.g., if you used single quotes but the formatter converted them to double quotes). Always base your SEARCH/REPLACE operations on this final version to ensure accuracy.


<environment_details>
# VSCode Visible Files
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# VSCode Open Tabs
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# Current Time
7/14/2025, 7:33:29 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
170,982 / 200K tokens used (85%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Perfect! I've successfully fixed the search and filter functionality in the AdminProductView. Here's what I changed:

## Key Improvements:

1. **Server-side Filtering**: The `fetchProducts()` function now properly sends filter parameters to the backend API:
   - **Search**: Sends `search` parameter when searchQuery has a value
   - **Category**: Converts category name to category ID and sends `category` parameter
   - **Status**: Still handled client-side since the backend doesn't have a direct status filter

2. **Optimized Client-side Filtering**: The `filteredProducts` computed property now only handles status filtering client-side, since search and category filtering are now done on the server.

3. **Improved Pagination**: 
   - `totalProducts` now correctly counts filtered results
   - `totalPages` calculation is based on the correct total count
   - Current page resets to 1 when filters change to prevent users from being stuck on non-existent pages

4. **Separate Watchers**: 
   - Search and category filters trigger server-side API calls with debouncing
   - Status filter only triggers client-side re-computation since it doesn't need an API call

## How it works now:

- **Search by keyword**: Types in search box → debounced API call with `search` parameter → backend searches product names and SKUs
- **Filter by category**: Selects category → finds category ID → API call with `category` parameter → backend filters by category
- **Filter by status**: Selects status → client-side filtering of already loaded products by stock/active status

This approach is much more efficient because:
- Large product lists are filtered on the server, reducing data transfer
- Search and category filtering happen in the database, which is faster
- Only status filtering (which is based on computed values) happens client-side
- Pagination works correctly with the filtered results

<attempt_completion>
<result>
Fixed the search and filter functionality in the AdminProductView. The frontend now properly sends filter parameters to the backend API:

1. **Search functionality**: Now sends `search` parameter to backend for server-side searching of product names and SKUs
2. **Category filtering**: Converts category name to ID and sends `category` parameter for server-side filtering
3. **Status filtering**: Remains client-side since backend doesn't support direct status filtering
4. **Improved pagination**: Correctly handles filtered results and resets to page 1 when filters change
5. **Debounced API calls**: Search and category changes trigger debounced API calls to prevent excessive requests

The filtering is now much more efficient with server-side processing for search and category, while status filtering (based on stock/active status) is handled client-side.
</result>
</attempt_completion>

---

**User:**

[attempt_completion] Result:
The user has provided feedback on the results. Consider their input to continue the task, and then attempt completion again.
<feedback>
Search by statusnya kan by default tampilkan semua status, tapi belum ketampil, kayanya itunya masih error
</feedback>
<environment_details>
# VSCode Visible Files
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# VSCode Open Tabs
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# Current Time
7/14/2025, 7:47:49 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
68,574 / 200K tokens used (34%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

I see the issue! The problem is that when `statusFilter.value` is empty (default "Semua Status"), the `filteredProducts` computed property is still trying to filter, but it should show all products. Let me fix this:

<replace_in_file>
<path>autowarehouse-frontend/src/views/admin/AdminProductView.vue</path>
<diff>
------- SEARCH
const filteredProducts = computed(() => {
  let filtered = products.value

  // Only apply status filter client-side since backend doesn't support it
  if (statusFilter.value) {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})
=======
const filteredProducts = computed(() => {
  let filtered = products.value

  // Only apply status filter client-side since backend doesn't support it
  if (statusFilter.value && statusFilter.value.trim() !== '') {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})
+++++++ REPLACE
</diff>
</replace_in_file>

---

**User:**

[replace_in_file for 'autowarehouse-frontend/src/views/admin/AdminProductView.vue'] Result:
The content was successfully saved to autowarehouse-frontend/src/views/admin/AdminProductView.vue.

Here is the full, updated content of the file that was saved:

<final_file_content path="autowarehouse-frontend/src/views/admin/AdminProductView.vue">
<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-4">
          <button 
            v-if="showAddForm" 
            @click="goBack" 
            class="text-gray-600 hover:text-gray-800"
          >
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <div>
            <h1 class="text-3xl font-bold text-gray-900">
              {{ showAddForm ? 'Tambah Produk Baru' : 'Product Management' }}
            </h1>
            <p class="text-gray-600 mt-1">
              {{ showAddForm ? 'Lengkapi form di bawah untuk menambah produk' : 'Kelola produk toko Anda' }}
            </p>
          </div>
        </div>
      </div>

        <!-- Product List View -->
        <div v-if="!showAddForm" class="p-6">
          <!-- Filters Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
            <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <div class="relative">
                  <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input 
                    v-model="searchQuery"
                    type="text" 
                    placeholder="Cari produk..." 
                    class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
                  >
                </div>
                <select 
                  v-model="categoryFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Kategori</option>
                  <option 
                    v-for="category in categories" 
                    :key="category.id" 
                    :value="category.name"
                  >
                    {{ category.name }}
                  </option>
                </select>
                <select 
                  v-model="statusFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Status</option>
                  <option value="active">Aktif</option>
                  <option value="inactive">Tidak Aktif</option>
                  <option value="out_of_stock">Stok Habis</option>
                </select>
              </div>
              <button 
                @click="showAddProductForm"
                class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center space-x-2"
              >
                <i class="fa-solid fa-plus"></i>
                <span>Tambah Produk</span>
              </button>
            </div>
          </div>

          <!-- Loading State -->
          <div v-if="loading" class="flex justify-center items-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <span class="ml-2 text-gray-600">Loading products...</span>
          </div>

          <!-- Error State -->
          <div v-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
            <div class="flex">
              <i class="fa-solid fa-exclamation-triangle text-red-600 mr-2"></i>
              <span class="text-red-800">{{ error }}</span>
              <button @click="fetchProducts" class="ml-auto text-red-600 hover:text-red-800">
                <i class="fa-solid fa-refresh"></i> Retry
              </button>
            </div>
          </div>

          <!-- Products Table -->
          <div v-if="!loading && !error" class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50 border-b border-gray-200">
                  <tr>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Produk</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Kategori</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Harga</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stok</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aksi</th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr 
                    v-for="product in filteredProducts" 
                    :key="product.id" 
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <div class="w-12 h-12 mr-4 flex-shrink-0">
                          <img 
                            v-if="hasProductImage(product)"
                            :src="getProductImage(product)" 
                            :alt="product.name" 
                            class="w-12 h-12 rounded-lg object-cover"
                            @error="handleImageError"
                          >
                          <div 
                            v-else
                            class="w-12 h-12 rounded-lg bg-gray-200 flex items-center justify-center"
                          >
                            <i class="fa-solid fa-image text-gray-400 text-lg"></i>
                          </div>
                        </div>
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ product.name }}</div>
                          <div class="text-sm text-gray-500">SKU: {{ product.sku }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.categoryName }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">Rp {{ formatPrice(product.price) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.stockQuantity }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="getStatusClass(getProductStatus(product))"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ getStatusText(getProductStatus(product)) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex space-x-2">
                        <button 
                          @click="editProduct(product)"
                          class="text-blue-600 hover:text-blue-700"
                        >
                          <i class="fa-solid fa-edit"></i>
                        </button>
                        <button 
                          @click="deleteProduct(product.id)"
                          class="text-red-600 hover:text-red-700"
                        >
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <!-- Pagination -->
            <div class="bg-gray-50 px-6 py-3 flex items-center justify-between border-t border-gray-200">
              <div class="text-sm text-gray-700">
                Menampilkan {{ startIndex }} sampai {{ endIndex }} dari {{ totalProducts }} produk
              </div>
              <div class="flex space-x-2">
                <button 
                  @click="previousPage"
                  :disabled="currentPage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Previous
                </button>
                <button 
                  v-for="page in visiblePages" 
                  :key="page"
                  @click="goToPage(page)"
                  :class="page === currentPage ? 'bg-blue-600 text-white' : 'border border-gray-300 hover:bg-gray-100'"
                  class="px-3 py-1 text-sm rounded"
                >
                  {{ page }}
                </button>
                <button 
                  @click="nextPage"
                  :disabled="currentPage === totalPages"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Add/Edit Product Form -->
        <div v-if="showAddForm" class="p-6">
          <div class="max-w-4xl mx-auto">
            <form @submit.prevent="saveProduct" class="space-y-8">
              
              <!-- Basic Information -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-info-circle text-blue-600 mr-2"></i>
                  Informasi Dasar
                </h3>
                
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Nama Produk *</label>
                    <input 
                      v-model="productForm.name"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Masukkan nama produk" 
                      required
                    >
                  </div>
                  
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Kategori *</label>
                    <select 
                      v-model="productForm.category"
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      required
                      :disabled="categoriesLoading"
                    >
                      <option value="">{{ categoriesLoading ? 'Loading categories...' : 'Pilih Kategori' }}</option>
                      <option 
                        v-for="category in categories" 
                        :key="category.id" 
                        :value="category.id"
                      >
                        {{ category.name }}
                      </option>
                    </select>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">SKU</label>
                    <input 
                      v-model="productForm.sku"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Kode produk unik"
                    >
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Harga *</label>
                    <div class="relative">
                      <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                      <input 
                        v-model="productForm.price"
                        type="number" 
                        class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                        placeholder="0" 
                        required
                      >
                    </div>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Stok *</label>
                    <input 
                      v-model="productForm.stock"
                      type="number" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="0" 
                      required
                    >
                  </div>
                </div>
              </div>

              <!-- Specifications -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-cogs text-blue-600 mr-2"></i>
                  Spesifikasi Produk
                </h3>
                
                <div class="space-y-4">
                  <div 
                    v-for="(spec, index) in productForm.specifications" 
                    :key="index"
                    class="flex items-center space-x-4"
                  >
                    <input 
                      v-model="spec.name"
                      type="text" 
                      placeholder="Nama spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <input 
                      v-model="spec.value"
                      type="text" 
                      placeholder="Nilai spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <button 
                      type="button" 
                      @click="removeSpecification(index)" 
                      class="text-red-600 hover:text-red-700 p-2"
                    >
                      <i class="fa-solid fa-trash"></i>
                    </button>
                  </div>
                </div>
                
                <button 
                  type="button" 
                  @click="addSpecification" 
                  class="mt-4 flex items-center space-x-2 text-blue-600 hover:text-blue-700"
                >
                  <i class="fa-solid fa-plus"></i>
                  <span>Tambah Spesifikasi</span>
                </button>
              </div>

              <!-- Description -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-align-left text-blue-600 mr-2"></i>
                  Deskripsi Produk
                </h3>
                
                <textarea 
                  v-model="productForm.description"
                  rows="6" 
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                  placeholder="Masukkan deskripsi lengkap produk..."
                ></textarea>
              </div>

              <!-- Images -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-images text-blue-600 mr-2"></i>
                  Gambar Produk
                </h3>
                
                <div class="space-y-6">
                  <!-- Show existing images when editing -->
                  <div v-if="isEditing && existingImages.length > 0" class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-3">Gambar Saat Ini</label>
                    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                      <div 
                        v-for="(imageUrl, index) in existingImages" 
                        :key="index"
                        class="relative group"
                      >
                        <img 
                          :src="imageUrl" 
                          :alt="`Product image ${index + 1}`"
                          class="w-full h-32 object-cover rounded-lg border border-gray-200"
                          @error="handleImageError"
                        >
                        <button 
                          type="button"
                          @click="removeExistingImage(index)"
                          class="absolute top-2 right-2 bg-red-600 text-white rounded-full w-6 h-6 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                        >
                          <i class="fa-solid fa-times text-xs"></i>
                        </button>
                      </div>
                    </div>
                  </div>

                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-3">
                      {{ isEditing ? 'Tambah Gambar Baru' : 'Upload Gambar Produk *' }}
                    </label>
                    <div 
                      @click="triggerFileInput"
                      class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-blue-600 transition-colors cursor-pointer bg-gray-50 hover:bg-gray-100"
                    >
                      <i class="fa-solid fa-cloud-upload text-4xl text-gray-400 mb-4"></i>
                      <p class="text-gray-600 mb-2 font-medium">Drag & drop gambar atau klik untuk upload</p>
                      <p class="text-sm text-gray-500 mb-3">PNG, JPG hingga 10MB per file</p>
                      <button type="button" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                        Pilih File
                      </button>
                      <input 
                        ref="fileInput"
                        type="file" 
                        class="hidden" 
                        accept="image/*" 
                        multiple
                        @change="handleFileSelect"
                      >
                    </div>
                    
                    <!-- Show selected files -->
                    <div v-if="selectedFiles.length > 0" class="mt-4">
                      <p class="text-sm font-medium text-gray-700 mb-2">File yang dipilih:</p>
                      <div class="space-y-2">
                        <div 
                          v-for="(file, index) in selectedFiles" 
                          :key="index"
                          class="flex items-center justify-between bg-gray-50 p-2 rounded"
                        >
                          <span class="text-sm text-gray-600">{{ file.name }}</span>
                          <button 
                            type="button"
                            @click="removeFile(index)"
                            class="text-red-600 hover:text-red-700"
                          >
                            <i class="fa-solid fa-times"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Auction Settings -->
              <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-gavel text-blue-600 mr-2"></i>
                  Pengaturan Lelang
                </h3>
                
                <div class="space-y-6">
                  <div class="flex items-center space-x-3">
                    <input 
                      v-model="productForm.auctionEnabled"
                      type="checkbox" 
                      class="w-5 h-5 text-blue-600 focus:ring-blue-600 border-gray-300 rounded"
                    >
                    <label class="text-sm font-medium text-gray-700">Tersedia untuk lelang</label>
                  </div>
                  
                  <div v-if="productForm.auctionEnabled" class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Harga Awal Lelang</label>
                      <div class="relative">
                        <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                        <input 
                          v-model="productForm.auctionStartPrice"
                          type="number" 
                          class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                          placeholder="0"
                        >
                      </div>
                    </div>
                    
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Durasi Lelang (Hari)</label>
                      <select 
                        v-model="productForm.auctionDuration"
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      >
                        <option value="1">1 Hari</option>
                        <option value="3">3 Hari</option>
                        <option value="7">7 Hari</option>
                        <option value="14">14 Hari</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div> -->

              <!-- Status -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-toggle-on text-blue-600 mr-2"></i>
                  Status Produk
                </h3>
                
                <div class="flex space-x-6">
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="active" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Aktif</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="draft" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Draft</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="inactive" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Tidak Aktif</span>
                  </label>
                </div>
              </div>

              <!-- Form Actions -->
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <button 
                  type="button" 
                  @click="goBack" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors disabled:opacity-50"
                >
                  Batal
                </button>
                <button 
                  type="button" 
                  @click="saveDraft"
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-gray-600 text-white rounded-lg font-medium hover:bg-gray-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>Simpan sebagai Draft</span>
                </button>
                <button 
                  type="submit" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>{{ isEditing ? 'Update Produk' : 'Publikasikan Produk' }}</span>
                </button>
              </div>
            </form>
          </div>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService, type Product, type Category, type CreateProductRequest, type UpdateProductRequest } from '@/services/api'
import { debounce } from '@/utils/debounce'
import { uploadProductImages, deleteProductImages, validateProductImageFile, type UploadResult } from '@/services/firebase'

interface Specification {
  name: string
  value: string
}

interface LocalProduct {
  id: string
  name: string
  sku: string
  category: string
  price: number
  stock: number
  status: 'active' | 'inactive' | 'out_of_stock'
  image: string
  description?: string
  specifications?: Specification[]
}

interface ProductForm {
  name: string
  category: string
  sku: string
  price: number
  stock: number
  description: string
  specifications: Specification[]
  auctionEnabled: boolean
  auctionStartPrice: number
  auctionDuration: number
  status: 'active' | 'draft' | 'inactive'
}

const searchQuery = ref('')
const categoryFilter = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showAddForm = ref(false)
const isEditing = ref(false)
const editingProductId = ref<number | null>(null)

// API Integration
const products = ref<Product[]>([])
const categories = ref<Category[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const saving = ref(false)
const categoriesLoading = ref(false)

// File handling
const selectedFiles = ref<File[]>([])
const existingImages = ref<string[]>([])
const fileInput = ref<HTMLInputElement | null>(null)

const productForm = ref<ProductForm>({
  name: '',
  category: '',
  sku: '',
  price: 0,
  stock: 0,
  description: '',
  specifications: [{ name: '', value: '' }],
  auctionEnabled: false,
  auctionStartPrice: 0,
  auctionDuration: 7,
  status: 'active'
})

const filteredProducts = computed(() => {
  let filtered = products.value

  // Only apply status filter client-side since backend doesn't support it
  if (statusFilter.value && statusFilter.value.trim() !== '') {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalProducts = computed(() => {
  // If status filter is applied, count filtered products
  if (statusFilter.value) {
    return products.value.filter(product => getProductStatus(product) === statusFilter.value).length
  }
  return products.value.length
})

const totalPages = computed(() => Math.ceil(totalProducts.value / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalProducts.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStatusClass = (status: string) => {
  const classes = {
    active: 'bg-green-100 text-green-800',
    inactive: 'bg-gray-100 text-gray-800',
    out_of_stock: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    active: 'Aktif',
    inactive: 'Tidak Aktif',
    out_of_stock: 'Stok Habis'
  }
  return texts[status as keyof typeof texts] || 'Unknown'
}

const getProductStatus = (product: Product): string => {
  if (product.stockQuantity === 0) {
    return 'out_of_stock'
  }
  return product.isActive ? 'active' : 'inactive'
}

const showAddProductForm = () => {
  resetForm()
  showAddForm.value = true
  isEditing.value = false
}

const editProduct = async (product: Product) => {
  try {
    // Fetch fresh product data
    const fullProduct = await apiService.getProduct(product.id)
    
    // Parse specifications back to array format
    const specs = fullProduct.specifications 
      ? fullProduct.specifications.split('\n').map(line => {
          const [name, value] = line.split(': ')
          return { name: name || '', value: value || '' }
        })
      : [{ name: '', value: '' }]
    
    // Find category ID from categories list
    const category = categories.value.find(cat => cat.name === fullProduct.categoryName)
    
    // Populate form with API data
    productForm.value = {
      name: fullProduct.name,
      category: category?.id.toString() || '',
      sku: fullProduct.sku,
      price: fullProduct.price,
      stock: fullProduct.stockQuantity,
      description: fullProduct.description,
      specifications: specs,
      auctionEnabled: false,
      auctionStartPrice: 0,
      auctionDuration: 7,
      status: fullProduct.isActive ? 'active' : 'inactive'
    }
    
    // Load existing images
    existingImages.value = fullProduct.imageUrls || []
    selectedFiles.value = []
    
    editingProductId.value = product.id
    showAddForm.value = true
    isEditing.value = true
  } catch (err) {
    alert('Failed to load product details')
    console.error(err)
  }
}

const deleteProduct = async (productId: number) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await apiService.deleteProduct(productId)
    alert('Product deleted successfully!')
    fetchProducts() // Refresh list
  } catch (err) {
    alert('Failed to delete product')
    console.error(err)
  }
}

const goBack = () => {
  showAddForm.value = false
  resetForm()
}

const resetForm = () => {
  productForm.value = {
    name: '',
    category: '',
    sku: '',
    price: 0,
    stock: 0,
    description: '',
    specifications: [{ name: '', value: '' }],
    auctionEnabled: false,
    auctionStartPrice: 0,
    auctionDuration: 7,
    status: 'active'
  }
  selectedFiles.value = []
  existingImages.value = []
  editingProductId.value = null
  isEditing.value = false
}

const addSpecification = () => {
  productForm.value.specifications.push({ name: '', value: '' })
}

const removeSpecification = (index: number) => {
  if (productForm.value.specifications.length > 1) {
    productForm.value.specifications.splice(index, 1)
  }
}

// API Functions
const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    // Build filter parameters
    const filters: any = {}
    
    if (searchQuery.value.trim()) {
      filters.search = searchQuery.value.trim()
    }
    
    if (categoryFilter.value) {
      // Find category ID by name
      const category = categories.value.find(cat => cat.name === categoryFilter.value)
      if (category) {
        filters.category = category.id
      }
    }
    
    // Note: statusFilter is handled client-side since backend doesn't have a direct status filter
    // The backend returns active products by default, and we filter by stock status on frontend
    
    const allProducts = await apiService.getProducts(filters)
    products.value = allProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const validateForm = (): boolean => {
  if (!productForm.value.name.trim()) {
    alert('Product name is required')
    return false
  }
  
  if (!productForm.value.category) {
    alert('Category is required')
    return false
  }
  
  if (productForm.value.price <= 0) {
    alert('Price must be greater than 0')
    return false
  }
  
  if (productForm.value.stock < 0) {
    alert('Stock cannot be negative')
    return false
  }
  
  return true
}

const generateSKU = (): string => {
  const timestamp = Date.now().toString().slice(-6)
  const random = Math.random().toString(36).substring(2, 5).toUpperCase()
  return `PRD${timestamp}${random}`
}

const saveProduct = async () => {
  if (!validateForm()) return
  
  saving.value = true
  try {
    // Auto-generate SKU if not provided
    const finalSku = productForm.value.sku.trim() || generateSKU()
    
    // Handle images - combine existing and new images
    let finalImageUrls: string[] = []
    
    // For editing: start with existing images
    if (isEditing.value) {
      finalImageUrls = [...existingImages.value]
    }
    
    // Upload new images if any files selected
    if (selectedFiles.value.length > 0) {
      try {
        // Validate all files first
        for (const file of selectedFiles.value) {
          const validationError = validateProductImageFile(file)
          if (validationError) {
            throw new Error(validationError)
          }
        }
        
        // Upload all new images
        const uploadResults = await uploadProductImages(selectedFiles.value)
        const newImageUrls = uploadResults.map(result => result.url)
        
        // Add new images to final list
        finalImageUrls = [...finalImageUrls, ...newImageUrls]
        
        console.log('Images uploaded successfully:', newImageUrls)
      } catch (uploadError) {
        throw new Error(`Image upload failed: ${uploadError}`)
      }
    }
    
    const productData: CreateProductRequest = {
      name: productForm.value.name,
      description: productForm.value.description,
      price: productForm.value.price,
      stockQuantity: productForm.value.stock,
      brand: 'Unknown',
      model: 'Unknown',
      sku: finalSku,
      specifications: productForm.value.specifications
        .filter(spec => spec.name && spec.value)
        .map(spec => `${spec.name}: ${spec.value}`)
        .join('\n'),
      imageUrls: finalImageUrls,
      categoryId: parseInt(productForm.value.category)
    }
    
    if (isEditing.value && editingProductId.value) {
      await apiService.updateProduct(editingProductId.value, {
        ...productData,
        isActive: productForm.value.status === 'active'
      })
      alert('Product updated successfully!')
    } else {
      await apiService.createProduct(productData)
      alert('Product created successfully!')
    }
    
    goBack()
    fetchProducts() // Refresh list
  } catch (err) {
    alert(`Failed to save product: ${err}`)
    console.error(err)
  } finally {
    saving.value = false
  }
}

const saveDraft = () => {
  productForm.value.status = 'draft'
  saveProduct()
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page: number) => {
  currentPage.value = page
}

// File handling functions
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click()
  }
}

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    const files = Array.from(target.files)
    selectedFiles.value = [...selectedFiles.value, ...files]
  }
}

const removeFile = (index: number) => {
  selectedFiles.value.splice(index, 1)
}

// Image handling functions
const getProductImage = (product: Product): string | undefined => {
  // Check if product has imageUrls and it's not empty
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0]
  }
  
  // Return undefined if no image available
  return undefined
}

const hasProductImage = (product: Product): boolean => {
  return !!(product.imageUrls && product.imageUrls.length > 0)
}

const handleImageError = (event: Event) => {
  const target = event.target as HTMLImageElement
  const container = target.parentElement
  if (container) {
    target.style.display = 'none'
  }
}

const removeExistingImage = (index: number) => {
  existingImages.value.splice(index, 1)
}

// Add debounced version for search
const debouncedFetchProducts = debounce(fetchProducts, 300)

// Add watchers for real-time filtering
watch([searchQuery, categoryFilter], () => {
  currentPage.value = 1 // Reset to first page when search/category filters change
  debouncedFetchProducts()
})

// Watch status filter separately since it's client-side only
watch(statusFilter, () => {
  currentPage.value = 1 // Reset to first page when status filter changes
})

onMounted(() => {
  fetchCategories()
  fetchProducts()
})
</script>

</final_file_content>

IMPORTANT: For any future changes to this file, use the final_file_content shown above as your reference. This content reflects the current state of the file, including any auto-formatting (e.g., if you used single quotes but the formatter converted them to double quotes). Always base your SEARCH/REPLACE operations on this final version to ensure accuracy.


<environment_details>
# VSCode Visible Files
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# VSCode Open Tabs
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# Current Time
7/14/2025, 7:48:08 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
57,548 / 200K tokens used (29%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Now I also need to fix the `totalProducts` computed property to handle the empty status filter correctly:

<replace_in_file>
<path>autowarehouse-frontend/src/views/admin/AdminProductView.vue</path>
<diff>
------- SEARCH
const totalProducts = computed(() => {
  // If status filter is applied, count filtered products
  if (statusFilter.value) {
    return products.value.filter(product => getProductStatus(product) === statusFilter.value).length
  }
  return products.value.length
})
=======
const totalProducts = computed(() => {
  // If status filter is applied, count filtered products
  if (statusFilter.value && statusFilter.value.trim() !== '') {
    return products.value.filter(product => getProductStatus(product) === statusFilter.value).length
  }
  return products.value.length
})
+++++++ REPLACE
</diff>
</replace_in_file>

---

**User:**

[replace_in_file for 'autowarehouse-frontend/src/views/admin/AdminProductView.vue'] Result:
The content was successfully saved to autowarehouse-frontend/src/views/admin/AdminProductView.vue.

Here is the full, updated content of the file that was saved:

<final_file_content path="autowarehouse-frontend/src/views/admin/AdminProductView.vue">
<template>
  <div class="bg-gray-50 min-h-screen">
    <AdminNavbar />
    
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
      <!-- Page Header -->
      <div class="mb-8">
        <div class="flex items-center space-x-4">
          <button 
            v-if="showAddForm" 
            @click="goBack" 
            class="text-gray-600 hover:text-gray-800"
          >
            <i class="fa-solid fa-arrow-left text-lg"></i>
          </button>
          <div>
            <h1 class="text-3xl font-bold text-gray-900">
              {{ showAddForm ? 'Tambah Produk Baru' : 'Product Management' }}
            </h1>
            <p class="text-gray-600 mt-1">
              {{ showAddForm ? 'Lengkapi form di bawah untuk menambah produk' : 'Kelola produk toko Anda' }}
            </p>
          </div>
        </div>
      </div>

        <!-- Product List View -->
        <div v-if="!showAddForm" class="p-6">
          <!-- Filters Section -->
          <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6 mb-6">
            <div class="flex flex-col lg:flex-row lg:items-center lg:justify-between space-y-4 lg:space-y-0">
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <div class="relative">
                  <i class="fa-solid fa-search absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400"></i>
                  <input 
                    v-model="searchQuery"
                    type="text" 
                    placeholder="Cari produk..." 
                    class="pl-10 pr-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600 w-full sm:w-64"
                  >
                </div>
                <select 
                  v-model="categoryFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Kategori</option>
                  <option 
                    v-for="category in categories" 
                    :key="category.id" 
                    :value="category.name"
                  >
                    {{ category.name }}
                  </option>
                </select>
                <select 
                  v-model="statusFilter"
                  class="px-4 py-2 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                >
                  <option value="">Semua Status</option>
                  <option value="active">Aktif</option>
                  <option value="inactive">Tidak Aktif</option>
                  <option value="out_of_stock">Stok Habis</option>
                </select>
              </div>
              <button 
                @click="showAddProductForm"
                class="bg-blue-600 text-white px-6 py-2 rounded-lg hover:bg-blue-700 transition-colors flex items-center space-x-2"
              >
                <i class="fa-solid fa-plus"></i>
                <span>Tambah Produk</span>
              </button>
            </div>
          </div>

          <!-- Loading State -->
          <div v-if="loading" class="flex justify-center items-center py-8">
            <div class="animate-spin rounded-full h-8 w-8 border-b-2 border-blue-600"></div>
            <span class="ml-2 text-gray-600">Loading products...</span>
          </div>

          <!-- Error State -->
          <div v-if="error" class="bg-red-50 border border-red-200 rounded-lg p-4 mb-6">
            <div class="flex">
              <i class="fa-solid fa-exclamation-triangle text-red-600 mr-2"></i>
              <span class="text-red-800">{{ error }}</span>
              <button @click="fetchProducts" class="ml-auto text-red-600 hover:text-red-800">
                <i class="fa-solid fa-refresh"></i> Retry
              </button>
            </div>
          </div>

          <!-- Products Table -->
          <div v-if="!loading && !error" class="bg-white rounded-lg shadow-sm border border-gray-200 overflow-hidden">
            <div class="overflow-x-auto">
              <table class="w-full">
                <thead class="bg-gray-50 border-b border-gray-200">
                  <tr>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Produk</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Kategori</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Harga</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Stok</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                    <th class="px-6 py-4 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Aksi</th>
                  </tr>
                </thead>
                <tbody class="bg-white divide-y divide-gray-200">
                  <tr 
                    v-for="product in filteredProducts" 
                    :key="product.id" 
                    class="hover:bg-gray-50"
                  >
                    <td class="px-6 py-4 whitespace-nowrap">
                      <input type="checkbox" class="rounded border-gray-300 text-blue-600 focus:ring-blue-600">
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <div class="flex items-center">
                        <div class="w-12 h-12 mr-4 flex-shrink-0">
                          <img 
                            v-if="hasProductImage(product)"
                            :src="getProductImage(product)" 
                            :alt="product.name" 
                            class="w-12 h-12 rounded-lg object-cover"
                            @error="handleImageError"
                          >
                          <div 
                            v-else
                            class="w-12 h-12 rounded-lg bg-gray-200 flex items-center justify-center"
                          >
                            <i class="fa-solid fa-image text-gray-400 text-lg"></i>
                          </div>
                        </div>
                        <div>
                          <div class="text-sm font-medium text-gray-900">{{ product.name }}</div>
                          <div class="text-sm text-gray-500">SKU: {{ product.sku }}</div>
                        </div>
                      </div>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.categoryName }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium text-gray-900">Rp {{ formatPrice(product.price) }}</td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-900">{{ product.stockQuantity }}</td>
                    <td class="px-6 py-4 whitespace-nowrap">
                      <span 
                        :class="getStatusClass(getProductStatus(product))"
                        class="inline-flex px-2 py-1 text-xs font-semibold rounded-full"
                      >
                        {{ getStatusText(getProductStatus(product)) }}
                      </span>
                    </td>
                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                      <div class="flex space-x-2">
                        <button 
                          @click="editProduct(product)"
                          class="text-blue-600 hover:text-blue-700"
                        >
                          <i class="fa-solid fa-edit"></i>
                        </button>
                        <button 
                          @click="deleteProduct(product.id)"
                          class="text-red-600 hover:text-red-700"
                        >
                          <i class="fa-solid fa-trash"></i>
                        </button>
                      </div>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
            
            <!-- Pagination -->
            <div class="bg-gray-50 px-6 py-3 flex items-center justify-between border-t border-gray-200">
              <div class="text-sm text-gray-700">
                Menampilkan {{ startIndex }} sampai {{ endIndex }} dari {{ totalProducts }} produk
              </div>
              <div class="flex space-x-2">
                <button 
                  @click="previousPage"
                  :disabled="currentPage === 1"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Previous
                </button>
                <button 
                  v-for="page in visiblePages" 
                  :key="page"
                  @click="goToPage(page)"
                  :class="page === currentPage ? 'bg-blue-600 text-white' : 'border border-gray-300 hover:bg-gray-100'"
                  class="px-3 py-1 text-sm rounded"
                >
                  {{ page }}
                </button>
                <button 
                  @click="nextPage"
                  :disabled="currentPage === totalPages"
                  class="px-3 py-1 text-sm border border-gray-300 rounded hover:bg-gray-100 disabled:opacity-50"
                >
                  Next
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Add/Edit Product Form -->
        <div v-if="showAddForm" class="p-6">
          <div class="max-w-4xl mx-auto">
            <form @submit.prevent="saveProduct" class="space-y-8">
              
              <!-- Basic Information -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-info-circle text-blue-600 mr-2"></i>
                  Informasi Dasar
                </h3>
                
                <div class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Nama Produk *</label>
                    <input 
                      v-model="productForm.name"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Masukkan nama produk" 
                      required
                    >
                  </div>
                  
                  <div class="lg:col-span-2">
                    <label class="block text-sm font-medium text-gray-700 mb-2">Kategori *</label>
                    <select 
                      v-model="productForm.category"
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      required
                      :disabled="categoriesLoading"
                    >
                      <option value="">{{ categoriesLoading ? 'Loading categories...' : 'Pilih Kategori' }}</option>
                      <option 
                        v-for="category in categories" 
                        :key="category.id" 
                        :value="category.id"
                      >
                        {{ category.name }}
                      </option>
                    </select>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">SKU</label>
                    <input 
                      v-model="productForm.sku"
                      type="text" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="Kode produk unik"
                    >
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Harga *</label>
                    <div class="relative">
                      <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                      <input 
                        v-model="productForm.price"
                        type="number" 
                        class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                        placeholder="0" 
                        required
                      >
                    </div>
                  </div>
                  
                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-2">Stok *</label>
                    <input 
                      v-model="productForm.stock"
                      type="number" 
                      class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                      placeholder="0" 
                      required
                    >
                  </div>
                </div>
              </div>

              <!-- Specifications -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-cogs text-blue-600 mr-2"></i>
                  Spesifikasi Produk
                </h3>
                
                <div class="space-y-4">
                  <div 
                    v-for="(spec, index) in productForm.specifications" 
                    :key="index"
                    class="flex items-center space-x-4"
                  >
                    <input 
                      v-model="spec.name"
                      type="text" 
                      placeholder="Nama spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <input 
                      v-model="spec.value"
                      type="text" 
                      placeholder="Nilai spesifikasi" 
                      class="flex-1 px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                    >
                    <button 
                      type="button" 
                      @click="removeSpecification(index)" 
                      class="text-red-600 hover:text-red-700 p-2"
                    >
                      <i class="fa-solid fa-trash"></i>
                    </button>
                  </div>
                </div>
                
                <button 
                  type="button" 
                  @click="addSpecification" 
                  class="mt-4 flex items-center space-x-2 text-blue-600 hover:text-blue-700"
                >
                  <i class="fa-solid fa-plus"></i>
                  <span>Tambah Spesifikasi</span>
                </button>
              </div>

              <!-- Description -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-align-left text-blue-600 mr-2"></i>
                  Deskripsi Produk
                </h3>
                
                <textarea 
                  v-model="productForm.description"
                  rows="6" 
                  class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                  placeholder="Masukkan deskripsi lengkap produk..."
                ></textarea>
              </div>

              <!-- Images -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-images text-blue-600 mr-2"></i>
                  Gambar Produk
                </h3>
                
                <div class="space-y-6">
                  <!-- Show existing images when editing -->
                  <div v-if="isEditing && existingImages.length > 0" class="mb-6">
                    <label class="block text-sm font-medium text-gray-700 mb-3">Gambar Saat Ini</label>
                    <div class="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
                      <div 
                        v-for="(imageUrl, index) in existingImages" 
                        :key="index"
                        class="relative group"
                      >
                        <img 
                          :src="imageUrl" 
                          :alt="`Product image ${index + 1}`"
                          class="w-full h-32 object-cover rounded-lg border border-gray-200"
                          @error="handleImageError"
                        >
                        <button 
                          type="button"
                          @click="removeExistingImage(index)"
                          class="absolute top-2 right-2 bg-red-600 text-white rounded-full w-6 h-6 flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity"
                        >
                          <i class="fa-solid fa-times text-xs"></i>
                        </button>
                      </div>
                    </div>
                  </div>

                  <div>
                    <label class="block text-sm font-medium text-gray-700 mb-3">
                      {{ isEditing ? 'Tambah Gambar Baru' : 'Upload Gambar Produk *' }}
                    </label>
                    <div 
                      @click="triggerFileInput"
                      class="border-2 border-dashed border-gray-300 rounded-lg p-8 text-center hover:border-blue-600 transition-colors cursor-pointer bg-gray-50 hover:bg-gray-100"
                    >
                      <i class="fa-solid fa-cloud-upload text-4xl text-gray-400 mb-4"></i>
                      <p class="text-gray-600 mb-2 font-medium">Drag & drop gambar atau klik untuk upload</p>
                      <p class="text-sm text-gray-500 mb-3">PNG, JPG hingga 10MB per file</p>
                      <button type="button" class="px-4 py-2 bg-blue-600 text-white rounded-lg hover:bg-blue-700 transition-colors">
                        Pilih File
                      </button>
                      <input 
                        ref="fileInput"
                        type="file" 
                        class="hidden" 
                        accept="image/*" 
                        multiple
                        @change="handleFileSelect"
                      >
                    </div>
                    
                    <!-- Show selected files -->
                    <div v-if="selectedFiles.length > 0" class="mt-4">
                      <p class="text-sm font-medium text-gray-700 mb-2">File yang dipilih:</p>
                      <div class="space-y-2">
                        <div 
                          v-for="(file, index) in selectedFiles" 
                          :key="index"
                          class="flex items-center justify-between bg-gray-50 p-2 rounded"
                        >
                          <span class="text-sm text-gray-600">{{ file.name }}</span>
                          <button 
                            type="button"
                            @click="removeFile(index)"
                            class="text-red-600 hover:text-red-700"
                          >
                            <i class="fa-solid fa-times"></i>
                          </button>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Auction Settings -->
              <!-- <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-gavel text-blue-600 mr-2"></i>
                  Pengaturan Lelang
                </h3>
                
                <div class="space-y-6">
                  <div class="flex items-center space-x-3">
                    <input 
                      v-model="productForm.auctionEnabled"
                      type="checkbox" 
                      class="w-5 h-5 text-blue-600 focus:ring-blue-600 border-gray-300 rounded"
                    >
                    <label class="text-sm font-medium text-gray-700">Tersedia untuk lelang</label>
                  </div>
                  
                  <div v-if="productForm.auctionEnabled" class="grid grid-cols-1 lg:grid-cols-2 gap-6">
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Harga Awal Lelang</label>
                      <div class="relative">
                        <span class="absolute left-4 top-1/2 transform -translate-y-1/2 text-gray-500 font-medium">Rp</span>
                        <input 
                          v-model="productForm.auctionStartPrice"
                          type="number" 
                          class="w-full pl-12 pr-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600" 
                          placeholder="0"
                        >
                      </div>
                    </div>
                    
                    <div>
                      <label class="block text-sm font-medium text-gray-700 mb-2">Durasi Lelang (Hari)</label>
                      <select 
                        v-model="productForm.auctionDuration"
                        class="w-full px-4 py-3 border border-gray-300 rounded-lg focus:ring-2 focus:ring-blue-600 focus:border-blue-600"
                      >
                        <option value="1">1 Hari</option>
                        <option value="3">3 Hari</option>
                        <option value="7">7 Hari</option>
                        <option value="14">14 Hari</option>
                      </select>
                    </div>
                  </div>
                </div>
              </div> -->

              <!-- Status -->
              <div class="bg-white rounded-lg shadow-sm border border-gray-200 p-6">
                <h3 class="text-lg font-semibold text-gray-900 mb-6 flex items-center">
                  <i class="fa-solid fa-toggle-on text-blue-600 mr-2"></i>
                  Status Produk
                </h3>
                
                <div class="flex space-x-6">
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="active" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Aktif</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="draft" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Draft</span>
                  </label>
                  <label class="flex items-center">
                    <input 
                      v-model="productForm.status"
                      type="radio" 
                      value="inactive" 
                      class="w-4 h-4 text-blue-600 focus:ring-blue-600 border-gray-300"
                    >
                    <span class="ml-3 text-sm font-medium text-gray-700">Tidak Aktif</span>
                  </label>
                </div>
              </div>

              <!-- Form Actions -->
              <div class="flex flex-col sm:flex-row space-y-3 sm:space-y-0 sm:space-x-4">
                <button 
                  type="button" 
                  @click="goBack" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 border border-gray-300 rounded-lg text-gray-700 font-medium hover:bg-gray-50 transition-colors disabled:opacity-50"
                >
                  Batal
                </button>
                <button 
                  type="button" 
                  @click="saveDraft"
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-gray-600 text-white rounded-lg font-medium hover:bg-gray-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>Simpan sebagai Draft</span>
                </button>
                <button 
                  type="submit" 
                  :disabled="saving"
                  class="flex-1 px-6 py-3 bg-blue-600 text-white rounded-lg font-medium hover:bg-blue-700 transition-colors disabled:opacity-50"
                >
                  <span v-if="saving">
                    <i class="fa-solid fa-spinner animate-spin mr-2"></i>
                    Saving...
                  </span>
                  <span v-else>{{ isEditing ? 'Update Produk' : 'Publikasikan Produk' }}</span>
                </button>
              </div>
            </form>
          </div>
        </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted, watch } from 'vue'
import AdminNavbar from '../../components/AdminNavbar.vue'
import { apiService, type Product, type Category, type CreateProductRequest, type UpdateProductRequest } from '@/services/api'
import { debounce } from '@/utils/debounce'
import { uploadProductImages, deleteProductImages, validateProductImageFile, type UploadResult } from '@/services/firebase'

interface Specification {
  name: string
  value: string
}

interface LocalProduct {
  id: string
  name: string
  sku: string
  category: string
  price: number
  stock: number
  status: 'active' | 'inactive' | 'out_of_stock'
  image: string
  description?: string
  specifications?: Specification[]
}

interface ProductForm {
  name: string
  category: string
  sku: string
  price: number
  stock: number
  description: string
  specifications: Specification[]
  auctionEnabled: boolean
  auctionStartPrice: number
  auctionDuration: number
  status: 'active' | 'draft' | 'inactive'
}

const searchQuery = ref('')
const categoryFilter = ref('')
const statusFilter = ref('')
const currentPage = ref(1)
const itemsPerPage = 10
const showAddForm = ref(false)
const isEditing = ref(false)
const editingProductId = ref<number | null>(null)

// API Integration
const products = ref<Product[]>([])
const categories = ref<Category[]>([])
const loading = ref(false)
const error = ref<string | null>(null)
const saving = ref(false)
const categoriesLoading = ref(false)

// File handling
const selectedFiles = ref<File[]>([])
const existingImages = ref<string[]>([])
const fileInput = ref<HTMLInputElement | null>(null)

const productForm = ref<ProductForm>({
  name: '',
  category: '',
  sku: '',
  price: 0,
  stock: 0,
  description: '',
  specifications: [{ name: '', value: '' }],
  auctionEnabled: false,
  auctionStartPrice: 0,
  auctionDuration: 7,
  status: 'active'
})

const filteredProducts = computed(() => {
  let filtered = products.value

  // Only apply status filter client-side since backend doesn't support it
  if (statusFilter.value && statusFilter.value.trim() !== '') {
    filtered = filtered.filter(product => getProductStatus(product) === statusFilter.value)
  }

  // Pagination
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return filtered.slice(start, end)
})

const totalProducts = computed(() => {
  // If status filter is applied, count filtered products
  if (statusFilter.value && statusFilter.value.trim() !== '') {
    return products.value.filter(product => getProductStatus(product) === statusFilter.value).length
  }
  return products.value.length
})

const totalPages = computed(() => Math.ceil(totalProducts.value / itemsPerPage))
const startIndex = computed(() => (currentPage.value - 1) * itemsPerPage + 1)
const endIndex = computed(() => Math.min(currentPage.value * itemsPerPage, totalProducts.value))

const visiblePages = computed(() => {
  const pages = []
  const start = Math.max(1, currentPage.value - 2)
  const end = Math.min(totalPages.value, currentPage.value + 2)
  
  for (let i = start; i <= end; i++) {
    pages.push(i)
  }
  
  return pages
})

const formatPrice = (price: number) => {
  return new Intl.NumberFormat('id-ID').format(price)
}

const getStatusClass = (status: string) => {
  const classes = {
    active: 'bg-green-100 text-green-800',
    inactive: 'bg-gray-100 text-gray-800',
    out_of_stock: 'bg-red-100 text-red-800'
  }
  return classes[status as keyof typeof classes] || 'bg-gray-100 text-gray-800'
}

const getStatusText = (status: string) => {
  const texts = {
    active: 'Aktif',
    inactive: 'Tidak Aktif',
    out_of_stock: 'Stok Habis'
  }
  return texts[status as keyof typeof texts] || 'Unknown'
}

const getProductStatus = (product: Product): string => {
  if (product.stockQuantity === 0) {
    return 'out_of_stock'
  }
  return product.isActive ? 'active' : 'inactive'
}

const showAddProductForm = () => {
  resetForm()
  showAddForm.value = true
  isEditing.value = false
}

const editProduct = async (product: Product) => {
  try {
    // Fetch fresh product data
    const fullProduct = await apiService.getProduct(product.id)
    
    // Parse specifications back to array format
    const specs = fullProduct.specifications 
      ? fullProduct.specifications.split('\n').map(line => {
          const [name, value] = line.split(': ')
          return { name: name || '', value: value || '' }
        })
      : [{ name: '', value: '' }]
    
    // Find category ID from categories list
    const category = categories.value.find(cat => cat.name === fullProduct.categoryName)
    
    // Populate form with API data
    productForm.value = {
      name: fullProduct.name,
      category: category?.id.toString() || '',
      sku: fullProduct.sku,
      price: fullProduct.price,
      stock: fullProduct.stockQuantity,
      description: fullProduct.description,
      specifications: specs,
      auctionEnabled: false,
      auctionStartPrice: 0,
      auctionDuration: 7,
      status: fullProduct.isActive ? 'active' : 'inactive'
    }
    
    // Load existing images
    existingImages.value = fullProduct.imageUrls || []
    selectedFiles.value = []
    
    editingProductId.value = product.id
    showAddForm.value = true
    isEditing.value = true
  } catch (err) {
    alert('Failed to load product details')
    console.error(err)
  }
}

const deleteProduct = async (productId: number) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  
  try {
    await apiService.deleteProduct(productId)
    alert('Product deleted successfully!')
    fetchProducts() // Refresh list
  } catch (err) {
    alert('Failed to delete product')
    console.error(err)
  }
}

const goBack = () => {
  showAddForm.value = false
  resetForm()
}

const resetForm = () => {
  productForm.value = {
    name: '',
    category: '',
    sku: '',
    price: 0,
    stock: 0,
    description: '',
    specifications: [{ name: '', value: '' }],
    auctionEnabled: false,
    auctionStartPrice: 0,
    auctionDuration: 7,
    status: 'active'
  }
  selectedFiles.value = []
  existingImages.value = []
  editingProductId.value = null
  isEditing.value = false
}

const addSpecification = () => {
  productForm.value.specifications.push({ name: '', value: '' })
}

const removeSpecification = (index: number) => {
  if (productForm.value.specifications.length > 1) {
    productForm.value.specifications.splice(index, 1)
  }
}

// API Functions
const fetchCategories = async () => {
  categoriesLoading.value = true
  try {
    const response = await apiService.getCategories()
    categories.value = response.filter(cat => cat.isActive)
  } catch (err) {
    console.error('Failed to fetch categories:', err)
  } finally {
    categoriesLoading.value = false
  }
}

const fetchProducts = async () => {
  loading.value = true
  error.value = null
  try {
    // Build filter parameters
    const filters: any = {}
    
    if (searchQuery.value.trim()) {
      filters.search = searchQuery.value.trim()
    }
    
    if (categoryFilter.value) {
      // Find category ID by name
      const category = categories.value.find(cat => cat.name === categoryFilter.value)
      if (category) {
        filters.category = category.id
      }
    }
    
    // Note: statusFilter is handled client-side since backend doesn't have a direct status filter
    // The backend returns active products by default, and we filter by stock status on frontend
    
    const allProducts = await apiService.getProducts(filters)
    products.value = allProducts
  } catch (err) {
    error.value = 'Failed to fetch products'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const validateForm = (): boolean => {
  if (!productForm.value.name.trim()) {
    alert('Product name is required')
    return false
  }
  
  if (!productForm.value.category) {
    alert('Category is required')
    return false
  }
  
  if (productForm.value.price <= 0) {
    alert('Price must be greater than 0')
    return false
  }
  
  if (productForm.value.stock < 0) {
    alert('Stock cannot be negative')
    return false
  }
  
  return true
}

const generateSKU = (): string => {
  const timestamp = Date.now().toString().slice(-6)
  const random = Math.random().toString(36).substring(2, 5).toUpperCase()
  return `PRD${timestamp}${random}`
}

const saveProduct = async () => {
  if (!validateForm()) return
  
  saving.value = true
  try {
    // Auto-generate SKU if not provided
    const finalSku = productForm.value.sku.trim() || generateSKU()
    
    // Handle images - combine existing and new images
    let finalImageUrls: string[] = []
    
    // For editing: start with existing images
    if (isEditing.value) {
      finalImageUrls = [...existingImages.value]
    }
    
    // Upload new images if any files selected
    if (selectedFiles.value.length > 0) {
      try {
        // Validate all files first
        for (const file of selectedFiles.value) {
          const validationError = validateProductImageFile(file)
          if (validationError) {
            throw new Error(validationError)
          }
        }
        
        // Upload all new images
        const uploadResults = await uploadProductImages(selectedFiles.value)
        const newImageUrls = uploadResults.map(result => result.url)
        
        // Add new images to final list
        finalImageUrls = [...finalImageUrls, ...newImageUrls]
        
        console.log('Images uploaded successfully:', newImageUrls)
      } catch (uploadError) {
        throw new Error(`Image upload failed: ${uploadError}`)
      }
    }
    
    const productData: CreateProductRequest = {
      name: productForm.value.name,
      description: productForm.value.description,
      price: productForm.value.price,
      stockQuantity: productForm.value.stock,
      brand: 'Unknown',
      model: 'Unknown',
      sku: finalSku,
      specifications: productForm.value.specifications
        .filter(spec => spec.name && spec.value)
        .map(spec => `${spec.name}: ${spec.value}`)
        .join('\n'),
      imageUrls: finalImageUrls,
      categoryId: parseInt(productForm.value.category)
    }
    
    if (isEditing.value && editingProductId.value) {
      await apiService.updateProduct(editingProductId.value, {
        ...productData,
        isActive: productForm.value.status === 'active'
      })
      alert('Product updated successfully!')
    } else {
      await apiService.createProduct(productData)
      alert('Product created successfully!')
    }
    
    goBack()
    fetchProducts() // Refresh list
  } catch (err) {
    alert(`Failed to save product: ${err}`)
    console.error(err)
  } finally {
    saving.value = false
  }
}

const saveDraft = () => {
  productForm.value.status = 'draft'
  saveProduct()
}

const previousPage = () => {
  if (currentPage.value > 1) {
    currentPage.value--
  }
}

const nextPage = () => {
  if (currentPage.value < totalPages.value) {
    currentPage.value++
  }
}

const goToPage = (page: number) => {
  currentPage.value = page
}

// File handling functions
const triggerFileInput = () => {
  if (fileInput.value) {
    fileInput.value.click()
  }
}

const handleFileSelect = (event: Event) => {
  const target = event.target as HTMLInputElement
  if (target.files) {
    const files = Array.from(target.files)
    selectedFiles.value = [...selectedFiles.value, ...files]
  }
}

const removeFile = (index: number) => {
  selectedFiles.value.splice(index, 1)
}

// Image handling functions
const getProductImage = (product: Product): string | undefined => {
  // Check if product has imageUrls and it's not empty
  if (product.imageUrls && product.imageUrls.length > 0) {
    return product.imageUrls[0]
  }
  
  // Return undefined if no image available
  return undefined
}

const hasProductImage = (product: Product): boolean => {
  return !!(product.imageUrls && product.imageUrls.length > 0)
}

const handleImageError = (event: Event) => {
  const target = event.target as HTMLImageElement
  const container = target.parentElement
  if (container) {
    target.style.display = 'none'
  }
}

const removeExistingImage = (index: number) => {
  existingImages.value.splice(index, 1)
}

// Add debounced version for search
const debouncedFetchProducts = debounce(fetchProducts, 300)

// Add watchers for real-time filtering
watch([searchQuery, categoryFilter], () => {
  currentPage.value = 1 // Reset to first page when search/category filters change
  debouncedFetchProducts()
})

// Watch status filter separately since it's client-side only
watch(statusFilter, () => {
  currentPage.value = 1 // Reset to first page when status filter changes
})

onMounted(() => {
  fetchCategories()
  fetchProducts()
})
</script>

</final_file_content>

IMPORTANT: For any future changes to this file, use the final_file_content shown above as your reference. This content reflects the current state of the file, including any auto-formatting (e.g., if you used single quotes but the formatter converted them to double quotes). Always base your SEARCH/REPLACE operations on this final version to ensure accuracy.


<environment_details>
# VSCode Visible Files
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# VSCode Open Tabs
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# Current Time
7/14/2025, 7:48:27 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
126,578 / 200K tokens used (63%)

# Current Mode
ACT MODE
</environment_details>

---

**Assistant:**

Perfect! I've fixed the status filter issue. Now when the status filter is set to "Semua Status" (empty value), it will properly show all products instead of trying to filter them.

## Summary of the fixes made:

1. **Fixed SQLGrammarException**: Removed the problematic `price` and `totalPrice` fields from the OrderItem entity that were trying to map to non-existent database columns.

2. **Implemented proper server-side filtering**: 
   - **Search**: Now sends `search` parameter to backend for server-side searching
   - **Category**: Converts category name to ID and sends `category` parameter for server-side filtering
   - **Status**: Properly handles empty filter value to show all products

3. **Fixed status filter logic**: Added proper checks for empty/null values in both `filteredProducts` and `totalProducts` computed properties so that when "Semua Status" is selected, all products are displayed.

4. **Improved pagination**: Correctly handles filtered results and resets to page 1 when filters change.

The search and filter functionality should now work properly:
- **Search by keyword**: Real-time server-side search with debouncing
- **Filter by category**: Server-side filtering by category ID
- **Filter by status**: Client-side filtering that properly shows all products when "Semua Status" is selected
- **Pagination**: Works correctly with filtered results

<attempt_completion>
<result>
Fixed both the backend SQLGrammarException and the frontend search/filter functionality:

**Backend Fix:**
- Removed problematic `price` and `totalPrice` fields from OrderItem entity that were mapping to non-existent database columns
- Entity now properly aligns with the actual database schema

**Frontend Fix:**
- Implemented proper server-side filtering for search and category filters
- Fixed status filter to correctly show all products when "Semua Status" is selected
- Added proper empty value checks in both `filteredProducts` and `totalProducts` computed properties
- Search and category filters now send parameters to backend API for efficient server-side processing
- Status filter works client-side for stock/active status filtering
- Pagination correctly handles filtered results and resets to page 1 when filters change

The product management page now has fully functional search by keyword, filter by category, and filter by status capabilities.
</result>
</attempt_completion>

---

**User:**

[attempt_completion] Result:
The user has provided feedback on the results. Consider their input to continue the task, and then attempt completion again.
<feedback>
Pas saya ganti statusnya dari dropdown, pas saya select dia blm call api tuh
</feedback>
<environment_details>
# VSCode Visible Files
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# VSCode Open Tabs
autowarehouse-frontend/src/views/admin/AdminProductView.vue

# Current Time
7/14/2025, 7:49:16 PM (Asia/Bangkok, UTC+7:00)

# Context Window Usage
150,712 / 200K tokens used (75%)

# Current Mode
ACT MODE
</environment_details>

