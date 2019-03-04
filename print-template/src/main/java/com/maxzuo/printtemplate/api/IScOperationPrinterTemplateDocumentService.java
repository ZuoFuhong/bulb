package com.maxzuo.printtemplate.api;

import com.maxzuo.printtemplate.model.ScOperationPrinterTemplateDocument;

import java.util.List;

/**
 * 打印模板-模板相关接口
 * Created by zfh on 2018/12/12
 */
public interface IScOperationPrinterTemplateDocumentService {

    /**
     * 添加模板
     * @param scShopPrinterTemplateDocument {@link ScOperationPrinterTemplateDocument}
     * @return 自增主键
     */
    Integer saveShopPrinterTemplateDocument (ScOperationPrinterTemplateDocument scShopPrinterTemplateDocument);

    /**
     * 根据主键模板查询
     * @param id 主键
     * @return {@link ScOperationPrinterTemplateDocument}
     */
    ScOperationPrinterTemplateDocument getShopPrinterTemplateDocumentByPrimaryId(Integer id);

    /**
     * 根据模板名称查询模板
     * @param name 模板名称
     * @return {@link ScOperationPrinterTemplateDocument}
     */
    ScOperationPrinterTemplateDocument getShopPrinterTemplateDocumentByName(String name);

    /**
     * 根据主键更新模板
     * @param scShopPrinterTemplateDocument {@link ScOperationPrinterTemplateDocument}
     * @return 受影响的行数
     */
    Integer updateShopPrinterTemplateDocumentByPrimaryId(ScOperationPrinterTemplateDocument scShopPrinterTemplateDocument);

    /**
     * 根据店铺id和票据类型 更新模板状态
     * @param shopId 店铺id
     * @param documentType 票据类型
     * @return 受影响条数
     */
    Integer updateShopPrinterTemplateDocumentStatusByShopIdAndDocumentType(Integer shopId, Integer documentType);

    /**
     * 移除自定义模板（软删除）
     * @param id 主键
     * @return 受影响的条数
     */
    Integer removeShopPrinterTemplateDocumentByPrimaryId(Integer id);

    /**
     * 查询店铺下模板列表和默认的模板
     * @param shopId 店铺id
     * @param documentType 票据类型
     * @return list
     */
    List<ScOperationPrinterTemplateDocument> listPrinterTemplateDocumentByShopIdAndDocumentType (Integer shopId, Integer documentType);
}
