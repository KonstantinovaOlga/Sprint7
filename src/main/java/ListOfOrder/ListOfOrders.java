package ListOfOrder;
import java.util.List;

public class ListOfOrders {
    public List<CreatedOrder> getCreatedOrders() {
        return orders;
    }

    public void setCreatedOrders(List<CreatedOrder> orders) {
        this.orders = orders;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<AvailableStation> getAvailableStations() {
        return availableStations;
    }

    public void setAvailableStations(List<AvailableStation> availableStations) {
        this.availableStations = availableStations;
    }

    private List<CreatedOrder> orders;
    private PageInfo pageInfo;
    private List<AvailableStation> availableStations;

    public ListOfOrders(List<CreatedOrder> orders, PageInfo pageInfo, List<AvailableStation> availableStations) {
        this.orders = orders;
        this.pageInfo = pageInfo;
        this.availableStations = availableStations;
    }

    public ListOfOrders() {
    }
}
