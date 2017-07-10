package vn.com.nsmv.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import vn.com.nsmv.bean.CustomUser;
import vn.com.nsmv.common.Constants;
import vn.com.nsmv.common.SokokanriException;
import vn.com.nsmv.common.Utils;
import vn.com.nsmv.dao.BillDAO;
import vn.com.nsmv.dao.CategoryDAO;
import vn.com.nsmv.dao.ItemDAO;
import vn.com.nsmv.dao.ItemHistoryDAO;
import vn.com.nsmv.entity.Bill;
import vn.com.nsmv.entity.Category;
import vn.com.nsmv.entity.Item;
import vn.com.nsmv.entity.ItemHistory;
import vn.com.nsmv.entity.User;
import vn.com.nsmv.i18n.SokokanriMessage;
import vn.com.nsmv.javabean.SearchCondition;
import vn.com.nsmv.javabean.SortCondition;
import vn.com.nsmv.javabean.UploadBean;
import vn.com.nsmv.service.OrdersService;

@Service("ordersService")
@EnableTransactionManagement
public class OrdersServiceImpl implements OrdersService {


    private Locale locale = LocaleContextHolder.getLocale();
    @Autowired
    private ItemDAO itemDAO;
    @Autowired
    private CategoryDAO categoryDAO;
    @Autowired
    private BillDAO billDAO;
    @Autowired
    private ItemHistoryDAO itemHistoryDAO;

    @Transactional(rollbackFor = {SokokanriException.class})
    public Long createOrder(Category category) throws SokokanriException {
        User user = new User();
        user.setId(category.getUserId());
        category.setUser(user);
        category.setStatus(0);
        category.validate();
        Long categoryId = categoryDAO.add(category);

        Iterator<Item> iterator = category.getItems().iterator();
        boolean hasRecord = false;
        while (iterator.hasNext()) {
            Item item = iterator.next();

            if (item == null) {
                continue;
            }
            item.validate();
            if (item.ignore()) {
                iterator.remove();
                continue;
            }
            hasRecord = true;
            item.setCategory(category);
            item.setUser(user);
            item.setStatus(0);
            this.itemDAO.add(item);
        }
        if (hasRecord) {
            return categoryId;
        }
        throw new SokokanriException(SokokanriMessage.getMessageErrorProvideAtLeastOneItem(this.locale));
    }

    @Transactional
    public List<Item> getItemsInCategory(Long categoryId) throws SokokanriException {
        return this.itemDAO.getItemsInCategory(categoryId);
    }

    @Transactional
    public Category getCategory(Long categoryId) throws SokokanriException {
        return this.categoryDAO.getById(categoryId);
    }

    @Transactional
    public void saveOrder(Category category) throws SokokanriException {
        User user = new User();
        user.setId(category.getUserId());
        category.setUser(user);
        category.validate();
        this.categoryDAO.saveCategory(category);
        Iterator<Item> iterator = category.getItems().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (item == null) {
                continue;
            }
            item.validate();
            if (item.ignore()) {
                iterator.remove();
                continue;
            }
            item.setCategory(category);
            item.setUser(user);
            if (item.getId() == null) {
                this.itemDAO.add(item);
            } else {
                this.saveItem(item);
            }
        }
    }

    @Transactional
    public void deleteItemById(Long id) throws SokokanriException {
        Item item = this.itemDAO.findById(id);
        if (item == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(locale));
        }
        if (item.getCategory().getStatus() != 0 && item.getCategory().getStatus() != -1) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorOrderIsApprovedCannotDelete(locale));
        }
        this.itemDAO.deleteById(id);
    }

    @Transactional
    public List<Item> getAllOrders(
        SearchCondition searchCondition, SortCondition sortCondition, Integer offset, Integer maxResults)
        throws SokokanriException {
        return this.itemDAO.getAllItems(searchCondition, sortCondition, offset, maxResults);
    }

    @Transactional
    public List<Bill> getAllBills(SearchCondition searchCondition, Integer offset, Integer maxResults)
        throws SokokanriException {
        return this.billDAO.getAllBills(searchCondition, offset, maxResults);
    }

    @Transactional
    public int countAllBills(SearchCondition searchCondition) throws SokokanriException {
        return this.billDAO.countAllBills(searchCondition);
    }

    @Transactional
    public int countAllItems(SearchCondition searchCondition) throws SokokanriException {
        return this.itemDAO.countAllItems(searchCondition);
    }

    @Transactional
    public void approve(Long id) throws SokokanriException {
        this.approveAnOrder(id);
    }

    private void approveAnOrder(Long id) throws SokokanriException {
        Item item = this.itemDAO.findById(id);
        if (item == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(locale));
        }
        if (item.getStatus() == null || (item.getStatus() != 0 && item.getStatus() != -1)) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorCannotApproveSelectedOrder(locale));
        }
        item.setApprovalNote("");
        item.setApprover(
            ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
        item.setApprovedDate(new Date());
        item.setStatus(1);
        this.itemDAO.saveOrUpdate(item);
    }

    @Transactional
    public void approveOrders(Set<Long> selectedItems) throws SokokanriException {
        for (Long id : selectedItems) {
            this.approveAnOrder(id);
        }
    }

    @Transactional
    public void noteAnOrder(Long id, String content) throws SokokanriException {
        Item item = this.itemDAO.findById(id);
        if (item == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(locale));
        }
        if (item.getStatus() == null || (item.getStatus() != 0 && item.getStatus() != -1)) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorCannotNoteSelectedOrder(locale));
        }
        item.setApprovalNote(content);
        item.setStatus(-1);
        item.setApprover(
            ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
        item.setApprovedDate(new Date());
        this.itemDAO.saveOrUpdate(item);
    }

    @Transactional
    public void saveRealPrice(Long id, Double value) throws SokokanriException {
        Item item = this.itemDAO.findById(id);
        if (item == null) {
            return;
        }
        item.setRealPrice(value);
        this.itemDAO.saveOrUpdate(item);
    }

    @Transactional
    public void noteABuyingOrder(Long id, String content) throws SokokanriException {
        Item item = this.itemDAO.findById(id);
        if (item == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(locale));
        }
        if (item.getStatus() == null || (item.getStatus() != 1 && item.getStatus() != -2)) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorCannotNoteSelectedOrder(locale));
        }
        item.setApprovalNote(content);
        item.setBuyer(((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
        item.setBoughtDate(new Date());
        item.setStatus(-1);
        this.itemDAO.saveOrUpdate(item);

    }

    @Transactional
    public void transferOrder(Long id) throws SokokanriException {
        this.transferAnOrder(id);
    }

    private void transferAnOrder(Long id) throws SokokanriException {
        Item item = this.itemDAO.findById(id);
        if (item == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(locale));
        }
        if (item.getStatus() == null || (item.getStatus() != 2)) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorCannotUpdateStatus(locale));
        }
        item.setTransported(
            ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
        item.setTransportedDate(new Date());
        item.setStatus(3);
        this.itemDAO.saveOrUpdate(item);
    }

    @Transactional
    public void transferOrders(Set<Long> selectedItems) throws SokokanriException {
        for (Long id : selectedItems) {
            this.transferAnOrder(id);
        }

    }

    @Transactional
    public void transferOrdersToVN(Set<Long> selectedItems, String tranferID) throws SokokanriException {
        for (Long id : selectedItems) {
            this.transferAnOrderToVN(id, tranferID);
        }

    }

    private void transferAnOrderToVN(Long id, String tranferID) throws SokokanriException {
        Item item = this.itemDAO.findById(id);
        if (item == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(locale));
        }
        if (item.getStatus() == null || (item.getStatus() != 3)) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorCannotUpdateStatus(locale));
        }
        item.setStatus(4);
        item.setTransporterVn(
            ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
        item.setTransportedVnDate(new Date());
        item.setTransferId(tranferID);
        this.itemDAO.saveOrUpdate(item);
    }

    @Transactional
    public void importToStorage(Map<Long, List<Item>> classificationOrders) throws SokokanriException {
        Iterator<Entry<Long, List<Item>>> iterator = classificationOrders.entrySet().iterator();
        while (iterator.hasNext()) {
            Bill bill = new Bill();
            bill.setStatus(1);
            Entry<Long, List<Item>> entry = iterator.next();
            this.billDAO.add(bill);
            for (Item item : entry.getValue()) {
                item.setBill(bill);
                item.setStatus(5);
                item.setChecker(
                    ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
                item.setCheckedDate(new Date());
                this.itemDAO.saveOrUpdate(item);
            }
        }
    }

    @Transactional
    public String exportBill(Long selectedItem, boolean toWeb) throws SokokanriException {
        Bill bill = this.billDAO.getById(selectedItem);
        if (bill == null || bill.getItems() == null || bill.getItems().isEmpty()) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorBillNotExist(locale));
        }
        StringBuilder content = new StringBuilder(SokokanriMessage.getLabelBillDetail(Utils.getFormattedId(bill.getId(), 7), locale));
        String breakLine = "\r\n";
        if (toWeb) {
            breakLine = "<br />";
        }
        content.append(breakLine);
        content.append(SokokanriMessage.getLabelNameOfCustomer(bill.getItems().get(0).getUser().getFullname(), locale));
        content.append(breakLine);
        content.append(String.format("%50s", "").replaceAll(" ", "="));
        content.append(breakLine);
        double total = 0;
        for (Item item : bill.getItems()) {
            if (toWeb) {
                content.append(String.format("%-50s", item.getName() + ":").replaceAll(" ", "&nbsp;"));
                content.append(breakLine);
                content.append(String.format("%50s", item.getComputePrice()).replaceAll(" ", "&nbsp;"));
            } else {
                content.append(String.format("%-50s", item.getName() + ":"));
                content.append(breakLine);
                content.append(String.format("%50s", item.getComputePrice()));
            }
            
            content.append(breakLine);
            total += item.getRealPrice();
        }
        content.append(breakLine);
        content.append(String.format("%50s", "").replaceAll(" ", "="));
        content.append(breakLine);
        if (toWeb) {
            content.append(String.format("%-50s", SokokanriMessage.getLabelTotalPrice(locale)).replaceAll(" ", "&nbsp;"));
            content.append(breakLine);
            content.append(String.format("%50s", total).replaceAll(" ", "&nbsp;"));
        } else {
            content.append(String.format("%-50s", SokokanriMessage.getLabelTotalPrice(locale)));
            content.append(breakLine);
            content.append(String.format("%50s", total));
        }
        content.append(breakLine);
        return content.toString();
    }

    @Transactional
    public void exportBill(Set<Long> selectedItems, boolean toWeb) throws SokokanriException {
        for (Long billId : selectedItems) {
            Bill bill = this.billDAO.getById(billId);
            if (bill == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorBillNotExist(locale));
            }
            for (Item item : bill.getItems()) {
                if (item.getStatus() == null || (item.getStatus() != 5)) {
                    throw new SokokanriException(SokokanriMessage.getMessageErrorCannotUpdateStatus(locale));
                }
                item.setStatus(6);
                item.setInformer(
                    ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
                item.setInformedDate(new Date());
                this.itemDAO.saveOrUpdate(item);

            }
        }

    }

    @Transactional
    public Long doUpload(UploadBean uploadBean) throws SokokanriException {
        Workbook workbook;
        ByteArrayInputStream byteArrayInputStream = null;
        try {
            MultipartFile uploadFile = uploadBean.getUploadFile();
            byteArrayInputStream = new ByteArrayInputStream(uploadFile.getBytes());
            if (uploadFile.getOriginalFilename().endsWith("xls")) {
                workbook = new HSSFWorkbook(byteArrayInputStream);
            } else if (uploadFile.getOriginalFilename().endsWith("xlsx")) {
                workbook = new XSSFWorkbook(byteArrayInputStream);
            } else {
                throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidExcel(locale));
            }
            Sheet firstSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = firstSheet.iterator();
            int index = 0;
            Category category = new Category();
            CustomUser customUser = (CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            User user = new User();
            user.setId(customUser.getUserId());
            category.setUser(user);
            category.setStatus(0);
            category.setFullName(uploadBean.getFullName());
            category.setPhone(uploadBean.getPhone());
            category.setAddress(uploadBean.getAddress());
            category.setEmail(uploadBean.getEmail());
            category.setNote(uploadBean.getNote());
            category.setCity(uploadBean.getCity());
            category.validate();
            Long result = categoryDAO.add(category);
            while (iterator.hasNext()) {

                Row nextRow = iterator.next();
                // We ignore the first row because it is the label of items
                if (index++ == 0) {
                    continue;
                }
                Iterator<Cell> cellIterator = nextRow.cellIterator();
                Item item = new Item();
                int columnIndex = 0;
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    item.setCategory(category);
                    switch (columnIndex) {
                        case 0:
                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                break;
                            }
                            if (!cell.getCellTypeEnum().equals(CellType.STRING)) {
                                throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidName(locale));
                            }
                            item.setName(cell.getStringCellValue());
                            break;

                        case 1:
                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                break;
                            }
                            if (!cell.getCellTypeEnum().equals(CellType.STRING)) {
                                throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidBrand(locale));
                            }
                            item.setBrand(cell.getStringCellValue());
                            break;
                        case 2:
                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                break;
                            }
                            if (!cell.getCellTypeEnum().equals(CellType.STRING)) {
                                throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidLink(locale));
                            }
                            item.setLink(cell.getStringCellValue());
                            break;
                        case 3:
                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                break;
                            }
                            if (!cell.getCellTypeEnum().equals(CellType.STRING)) {
                                throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidNote(locale));
                            }
                            item.setDescription(cell.getStringCellValue());
                            break;
                        case 4:
                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                break;
                            }
                            if (!cell.getCellTypeEnum().equals(CellType.NUMERIC)) {
                                throw new SokokanriException(SokokanriMessage.getMessageErrorInvalidCost(locale));
                            }
                            item.setCost(cell.getNumericCellValue());
                            break;
                        case 5:
                            if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                                break;
                            }
                            cell.setCellType(CellType.STRING);
                            String value = cell.getStringCellValue();
                            try {
                                int quantity = Integer.parseInt(value);
                                item.setQuantity(quantity);
                            } catch (Exception ex) {
                                throw new SokokanriException(SokokanriMessage.getMessageErrorQuantityMustGtZero(locale));
                            }
                            break;
                        default:
                            break;
                    }

                    columnIndex++;
                }
                if (item.ignore()) {
                    continue;
                }
                item.validate();
                item.setTotal(item.getQuantity() * item.getCost());
                item.setUser(user);
                item.setStatus(0);
                this.itemDAO.add(item);
            }

            workbook.close();
            return result;
        } catch (IOException ex) {
            throw new SokokanriException(ex);
        } finally {
            if (byteArrayInputStream != null) {
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                }
            }
        }
    }

    @Transactional
    public void alreadyToSend(Set<Long> selectedItems) throws SokokanriException {
        for (Long billId : selectedItems) {
            Bill bill = this.billDAO.getById(billId);
            if (bill == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorBillNotExist(locale));
            }
            for (Item item : bill.getItems()) {
                if (item.getStatus() == null || (item.getStatus() != 6)) {
                    throw new SokokanriException(SokokanriMessage.getMessageErrorCannotUpdateStatus(locale));
                }
                item.setStatus(7);
                this.itemDAO.saveOrUpdate(item);

            }
        }
    }

    @Transactional
    public void sendOrders(Set<Long> selectedItems) throws SokokanriException {
        for (Long billId : selectedItems) {
            Bill bill = this.billDAO.getById(billId);
            if (bill == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorBillNotExist(locale));
            }

            for (Item item : bill.getItems()) {
                if (item.getStatus() == null || (item.getStatus() != 7)) {
                    throw new SokokanriException(SokokanriMessage.getMessageErrorCannotUpdateStatus(locale));
                }
                item.setStatus(8);
                this.itemDAO.saveOrUpdate(item);

            }
        }
    }

    @Transactional
    public void deleteOrders(Set<Long> selectedItems) throws SokokanriException {
        for (Long id : selectedItems) {
            this.categoryDAO.deleteOrder(id);
        }
    }

    @Transactional
    public void saveOrder(Item item) throws SokokanriException {
        this.itemDAO.saveOrUpdate(item);
    }

    @Transactional
    public Item getItem(Long id) throws SokokanriException {
        return this.itemDAO.findById(id);
    }

    @Transactional
    public void saveItem(Item item) throws SokokanriException {
        Long id = item.getId();
        Item destinationItem = this.itemDAO.findById(id);
        if (destinationItem == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(locale));
        }
        destinationItem.setName(item.getName());
        destinationItem.setBrand(item.getBrand());
        destinationItem.setLink(item.getLink());
        destinationItem.setDescription(item.getDescription());
        destinationItem.setQuantity(item.getQuantity());
        destinationItem.setCost(item.getCost());
        destinationItem.setTotal(item.getQuantity() * item.getCost());
        destinationItem.setRealCost(item.getRealCost());
        destinationItem.setRealQuantity(item.getRealQuantity());
        if (item.getRealCost() != null && item.getRealQuantity() != null) {
            destinationItem.setRealPrice(item.getRealCost() * item.getRealQuantity());
        }
        destinationItem.setComputeCost(item.getComputeCost());
        if (item.getComputeCost() != null && item.getRealQuantity() != null) {
            destinationItem.setComputePrice(item.getComputeCost() * item.getRealQuantity());
        }
        destinationItem.setBuyingCode(item.getBuyingCode());
        this.itemDAO.saveOrUpdate(destinationItem);

        if (Utils.hasRole(Constants.ROLE_C)
            && (destinationItem.getStatus() == 0 || destinationItem.getStatus() == -1)) {
            // if current user is checker and item is not approved yet
            // we save the history of update order
            this.itemHistoryDAO.add(new ItemHistory(destinationItem));
        }
    }

    @Transactional
    public List<String> getAllBrands(Long userId, Integer status) throws SokokanriException {
        return this.itemDAO.getAllBrands(userId, status);
    }

    @Transactional
    public List<String> getAllBuyingCodes(Long userId, Integer status) throws SokokanriException {
        return this.itemDAO.getAllBuyingCodes(userId, status);
    }
    
    @Transactional
    public List<String> getAllTransferIds(Long userId, Integer status) throws SokokanriException {
        return this.itemDAO.getAllTransferId(userId, status);
    }

    @Transactional
    public void deleteItems(Set<Long> selectedItems) throws SokokanriException {
        for (Long id : selectedItems) {
            this.itemDAO.deleteById(id);
        }
    }

    @Transactional
    public void cancelItems(Set<Long> selectedItems) throws SokokanriException {
        for (Long id : selectedItems) {
            Item item = this.itemDAO.findById(id);
            if (item == null) {
                continue;
            }
            item.setStatus(-5);
            this.itemDAO.saveOrUpdate(item);
        }
    }

    @Transactional
    public void removeNote(Long id) throws SokokanriException {
        Item item = this.itemDAO.findById(id);
        if (item == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(locale));
        }
        item.setStatus(0);
        item.setApprovalNote("");
        this.itemDAO.saveOrUpdate(item);
    }

    @Transactional
    public void buyOrders(Set<Long> selectedItems) throws SokokanriException {

        for (Long id : selectedItems) {
            Item item = this.itemDAO.findById(id);
            if (item == null) {
                continue;
            }
            if (item.getRealCost() == null) {
                throw new SokokanriException(SokokanriMessage.getMessageErrorCostCannotBeEmptyWithParam(item.getFormattedId(), locale));
            }
            if (item.getRealQuantity() == null) {
                throw new SokokanriException(
                    SokokanriMessage.getMessageErrorQuantityCannotBeEmptyWithParam(item.getFormattedId(), locale));
            }
            if (item.getComputeCost() == null) {
                throw new SokokanriException(
                    SokokanriMessage.getMessageErrorCostCannotBeEmptyWithParam(item.getFormattedId(), locale));
            }
            if (Utils.isEmpty(item.getBuyingCode())) {
                throw new SokokanriException(
                    SokokanriMessage.getMessageErrorBuydingCodeCannotBeEmpty(item.getFormattedId(), locale));
            }
            item.setStatus(2);
            item.setBuyer(
                ((CustomUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUserId());
            item.setBoughtDate(new Date());
            item.setBuyNote("");

        }

    }

    @Transactional
    public Long removeFromBill(Long itemId) throws SokokanriException {
        Item item = this.itemDAO.findById(itemId);
        if (item == null) {
            throw new SokokanriException(SokokanriMessage.getMessageErrorOrderNotExist(locale));
        }
        Bill bill = item.getBill();
        Long billId = null;
        if (bill.getItems().size() == 1) {
            billId = bill.getId();
            this.billDAO.deleteBill(bill.getId());
        }
        //roll back the status of item to transferred to VN
        item.setBill(null);
        item.setStatus(4);
        item.setCheckedDate(null);
        item.setChecker(null);
        this.itemDAO.saveOrUpdate(item);
        return billId;
    }

    @Transactional
    public List<Long> getAllBillIDs(SearchCondition searchCondition) throws SokokanriException {
        return this.billDAO.getAllBillIDs(searchCondition);
    }


}
