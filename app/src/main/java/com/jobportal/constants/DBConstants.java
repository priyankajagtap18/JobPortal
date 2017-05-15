package com.jobportal.constants;

/**
 * Created by AsishG on 18-04-2016.
 */
public interface DBConstants {

    String TableCustOrderStatus = "CustOrderStatus";
    String custOrderYear = "year";
    String remainingQty = "remainingQty";
    String orderedQty = "orderedQty";
    String shippedQty = "shippedQty";
    String custOrderMonthNo = "monthNo";
    String custOrderMonthName = "monthName";
    String custUserName = "custUserName";

    String TableAdvanceShipmentNotice = "AdvanceShipmentNotice";
    String orderQuantityUom = "orderQuantityUom";
    String headerId = "headerId";
    String grade = "grade";
    String description = "description";
    String year = "year";
    String custAccountId = "custAccountId";
    String itemLength = "itemLength";
    String inventoryItemId = "inventoryItemId";
    String transactionalCurrCode = "transactionalCurrCode";
    String orderNumber = "orderNumber";
    String soldTo = "soldTo";
    String orderedItem = "orderedItem";
    String itemSize = "itemSize";
    String detailsImage = "detailsImage";
    String monthNum = "monthNum";
    String month = "month";
    String orderQty = "orderQty";
    String creationDate = "creationDate";
    String shipNoticeUserName = "shipNoticeUserName";

    String TableShipmentDetail = "ShipmentDetail";
    String shipDetailUltimateDropOffDate = "UltimateDropOffDate";
    String shipDetailCustomerName = "CustomerName";
    String shipDetailHeaderId = "HeaderId";
    String shipDetailCarrierName = "CarrierName";
    String shipDetailBundles = "Bundles";
    String shipDetailDestination = "Destination";
    String shipDetailItemSize = "ItemSize";
    String shipDetailDeliveryName = "DeliveryName";
    String shipDetailQty = "DetailQty";
    String shipDetailCustomerId = "CustomerId";
    String shipDetailInventoryItemId = "InventoryItemId";
    String shipDetailUserName = "shipDetailUserName";

    String TableAllOrder = "AllOrder";
    String allOrderUserName = "allOrderUserName";
    String orderType = "orderType";
    String allOrderHeaderId = "headerId";
    String allOrderCustAccountId = "custAccountId";
    String allOrderYear = "year";
    String allOrderRemainingQty = "remainingQty";
    String allOrderOrderedQty = "orderedQty";
    String allOrderShippedQty = "shippedQty";
    String allOrderMonthNo = "monthNo";
    String allOrderMonthName = "monthName";
    String allOrderOrderNo = "orderNo";

    String TableCustOrderDetail = "CustOrderDetail";
    String detailUserName = "detailUserName";
    String detailOrderQtyUOM = "OrderQuantityUOM";
    String detailHeaderId = "headerId";
    String detailDescription = "description";
    String detailCustAccId = "custAccountId";
    String detailCustomerId = "customerId";
    String detailInvItemId = "InvItemId";
    String detailStatus = "status";
    String detailTransCurrCode = "transactionCurrCode";
    String detailCreationDate = "creationDate";
    String detailOrderNo = "orderNo";
    String detailSoldTo = "SOLDtO";
    String detailOrderedItem = "orderedItem";
    String detailRemQty = "remainingQty";
    String detailShippedQty = "shippedQty";
    String detailOrderQty = "orderQty";
    String detailCustomerNo = "customerNo";
    String detailFlowStatusCode = "flowStatusCode";

    String TablePriceListing = "PriceListing";
    String priceListLocation = "location";
    String priceUserName = "priceUserName";
    String priceListESize = "eSize";
    String priceListGrade = "grade";
    String priceListListPrice = "listPrice";
    String priceListLength = "length";

    String TableCreditBalance = "creditBalance";
    String creditUserName = "creditUserName";
    String creditScheduledDeliveryAmount = "scheduledDeliveryAmount";
    String creditExpiryDate = "expiryDate";
    String creditCustomerId = "customerId";
    String creditlcNumber = "lcNumber";
    String creditlcbgtAmount = "lcbgtAmount";
    String creditAvailableBalance = "availableBalance";
    String creditIssuingBankName = "issuingBankName";
    String creditDocumentType = "documentType";
    String creditNegotiatingBankName = "negotiatingBankName";
    String creditInvoiceAmount = "invoiceAmount";
    String creditReceiptAmount = "receiptAmount";
    String creditPartyName = "partyName";
    String creditBankBranchName = "bankBranchName";
    String creditCurrency = "currency";
    String creditPendingOmDeliveryAmount = "pendingOmDeliveryAmount";
    String creditStartDate = "startDate";
    String creditNegotiatingBankBranchName = "negotiatingBankBranchName";
    String creditlcType = "lcType";
    String creditUninvoicedAmount = "uninvoicedAmount";

    String TableItemDeliveryDetail = "ItemDeliveryDetail";
    String itemHeaderId = "headerId";
    String itemUserName = "itemUserName";
    String itemInitialPackupDate = "initialPackupDate";
    String itemTruckNo = "truckNo";
    String itemShippedQty = "shippedQty";
    String itemInvItemId = "invItemId";
    String itemCustomerId = "customerId";
    String itemNoOfBundles = "noOfBundles";
    String itemStatus = "status";
    String itemDriverName = "driverName";
    String itemModeOfTransport = "modeOfTransport";
    String itemShippedFrom = "shippedFrom";
    String itemShippedToLoc = "shippedToLoc";
    String itemDeliveryName = "deliveryName";
    String itemShippedQtyUOM = "shippedQtyUOM";
    String itemTransporter = "transporter";

    String TableNewsAnnouncements = "NewsAnnouncement";
    String newsImagePath = "imagePath";
    String newsLastUpdateLogin = "lastUpdateLogin";
    String newsRecordId = "recordId";
    String newsHeading = "newsHeading";
    String newsDescription = "description";
    String newsCreatedBy = "createdBy";
    String newsCreationDate = "creationDate";
    String newslastUpdateDate = "lastUpdateDate";
    String newslastUpdatedBy = "lastUpdatedBy";
    String newsStatus = "status";

    String TableLogin = "Login";
    String loginUserName = "userName";
    String loginPassword = "password";
    String loginId = "id";
    String loginSessionToken="sessionToken";
}
