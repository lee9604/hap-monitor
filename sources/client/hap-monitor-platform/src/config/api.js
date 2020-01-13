//域名
const domain = window.location.host + "/api";

//端口
const port = null;

//路径
const root = window.location.protocol + "//" + domain + (port ? (":" + port) : "");

//接口
const api = {
    goods: {
        list: `${root}/goods/list`,
        add: `${root}/goods/add`,
        update: `${root}/goods/update`,
        detail: `${root}/goods/detail`,
        delete: `${root}/goods/delete`,
        batchDelete: `${root}/goods/batchDelete`,
    },
    goodsCategory: {
        list: `${root}/goodsCategory/list`,
        add: `${root}/goodsCategory/add`,
        update: `${root}/goodsCategory/update`,
        detail: `${root}/goodsCategory/detail`,
        delete: `${root}/goodsCategory/delete`,
        batchDelete: `${root}/goodsCategory/batchDelete`,
    },
    purchaseOrder: {
        list: `${root}/purchaseOrder/list`,
        add: `${root}/purchaseOrder/add`,
        update: `${root}/purchaseOrder/update`,
        detail: `${root}/purchaseOrder/detail`,
        delete: `${root}/purchaseOrder/delete`,
        batchDelete: `${root}/purchaseOrder/batchDelete`,
    },
    shipmentOrder: {
        list: `${root}/shipmentOrder/list`,
        add: `${root}/shipmentOrder/add`,
        update: `${root}/shipmentOrder/update`,
        detail: `${root}/shipmentOrder/detail`,
        delete: `${root}/shipmentOrder/delete`,
        batchDelete: `${root}/shipmentOrder/batchDelete`,
    },
    supplier: {
        list: `${root}/supplier/list`,
        add: `${root}/supplier/add`,
        update: `${root}/supplier/update`,
        detail: `${root}/supplier/detail`,
        delete: `${root}/supplier/delete`,
        batchDelete: `${root}/supplier/batchDelete`,
    },
    scafeNotice: {
        list: `${root}/scafeNotice/list`,
        add: `${root}/scafeNotice/add`,
        update: `${root}/scafeNotice/update`,
        detail: `${root}/scafeNotice/detail`,
        delete: `${root}/scafeNotice/delete`,
        batchDelete: `${root}/scafeNotice/batchDelete`,
    },
    feedback: {
        list: `${root}/feedback/list`,
        add: `${root}/feedback/add`,
        update: `${root}/feedback/update`,
        detail: `${root}/feedback/detail`,
        delete: `${root}/feedback/delete`,
        batchDelete: `${root}/feedback/batchDelete`,
    },
    feedbackReply: {
        list: `${root}/feedbackReply/list`,
        add: `${root}/feedbackReply/add`,
        update: `${root}/feedbackReply/update`,
        detail: `${root}/feedbackReply/detail`,
        delete: `${root}/feedbackReply/delete`,
        batchDelete: `${root}/feedbackReply/batchDelete`,
    },
    scafeArticle: {
        list: `${root}/scafeArticle/list`,
        add: `${root}/scafeArticle/add`,
        update: `${root}/scafeArticle/update`,
        detail: `${root}/scafeArticle/detail`,
        delete: `${root}/scafeArticle/delete`,
        batchDelete: `${root}/scafeArticle/batchDelete`,
        uploadArticleImg: `${root}/scafeArticle/uploadArticleImg`,
    }, scafeMenu: {
        list: `${root}/scafeMenu/list`,
        add: `${root}/scafeMenu/add`,
        update: `${root}/scafeMenu/update`,
        detail: `${root}/scafeMenu/detail`,
        delete: `${root}/scafeMenu/delete`,
        batchDelete: `${root}/scafeMenu/batchDelete`,
        uploadMenuImg: `${root}/scafeMenu/uploadMenuImg`,
    },
    scafeArtCategory: {
        list: `${root}/scafeArtCategory/list`,
        add: `${root}/scafeArtCategory/add`,
        update: `${root}/scafeArtCategory/update`,
        detail: `${root}/scafeArtCategory/detail`,
        delete: `${root}/scafeArtCategory/delete`,
        batchDelete: `${root}/scafeArtCategory/batchDelete`,
        tree: `${root}/scafeArtCategory/tree`,
    },
    canteen: {
        list: `${root}/canteen/list`,
        add: `${root}/canteen/add`,
        update: `${root}/canteen/update`,
        detail: `${root}/canteen/detail`,
        delete: `${root}/canteen/delete`,
        batchDelete: `${root}/canteen/batchDelete`
    },
    mealPriceStandard: {
        list: `${root}/mealPriceStandard/list`,
        add: `${root}/mealPriceStandard/add`,
        update: `${root}/mealPriceStandard/update`,
        detail: `${root}/mealPriceStandard/detail`,
        delete: `${root}/mealPriceStandard/delete`,
        batchDelete: `${root}/mealPriceStandard/batchDelete`
    },
    mealOrderSetting: {
        list: `${root}/mealOrderSetting/list`,
        add: `${root}/mealOrderSetting/add`,
        update: `${root}/mealOrderSetting/update`,
        detail: `${root}/mealOrderSetting/detail`,
        delete: `${root}/mealOrderSetting/delete`,
        batchDelete: `${root}/mealOrderSetting/batchDelete`
    },
    scafeOrder: {
        list: `${root}/scafeOrder/list`,
        add: `${root}/scafeOrder/add`,
        approve: `${root}/scafeOrder/approve`,
        update: `${root}/scafeOrder/update`,
        detail: `${root}/scafeOrder/detail`,
        delete: `${root}/scafeOrder/delete`,
        statisticsOfDate: `${root}/scafeOrder/statisticsOfDate`,
        statisticsOfMonth: `${root}/scafeOrder/statisticsOfMonth`,
        personCostStatisticsOfMonth: `${root}/scafeOrder/personCostStatisticsOfMonth`,
        exportPersonCostStatisticsOfMonth: `${root}/scafeOrder/exportPersonCostStatisticsOfMonth`,
        batchDelete: `${root}/scafeOrder/batchDelete`
    },
    scafePvsnOrder: {
        list: `${root}/scafePvsnOrder/list`,
        add: `${root}/scafePvsnOrder/add`,
        update: `${root}/scafePvsnOrder/update`,
        detail: `${root}/scafePvsnOrder/detail`,
        delete: `${root}/scafePvsnOrder/delete`,

        batchDelete: `${root}/scafePvsnOrder/batchDelete`
    },
    scafePvsnOrderMember: {
        list: `${root}/scafePvsnOrderMember/list`,
        add: `${root}/scafePvsnOrderMember/add`,
        update: `${root}/scafePvsnOrderMember/update`,
        detail: `${root}/scafePvsnOrderMember/detail`,
        delete: `${root}/scafePvsnOrderMember/delete`,
        batchDelete: `${root}/scafePvsnOrderMember/batchDelete`
    }, scafeEmployee: {
        list: `${root}/scafeEmployee/list`,
        configDeptManager: `${root}/scafeEmployee/configDeptManager`,
        add: `${root}/scafeEmployee/add`,
        update: `${root}/scafeEmployee/update`,
        detail: `${root}/scafeEmployee/detail`,
        delete: `${root}/scafeEmployee/delete`,
        batchDelete: `${root}/scafeEmployee/batchDelete`
    },
    scafeMenuSetting: {
        list: `${root}/scafeMenuSetting/list`,
        latestDetail: `${root}/scafeMenuSetting/latestDetail`,
        add: `${root}/scafeMenuSetting/add`,
        update: `${root}/scafeMenuSetting/update`,
        detail: `${root}/scafeMenuSetting/detail`,
        delete: `${root}/scafeMenuSetting/delete`,
        batchDelete: `${root}/scafeMenuSetting/batchDelete`
    },
    takePwdSetting: {
        configPwd: `${root}/takePwdSetting/configPwd`,
        currentPwd: `${root}/takePwdSetting/currentPwd`

    }
};

export default Object.assign(
    {
        $base: {
            domain: domain,
            port: port,
            root: root,
        },


    },
    api
)
