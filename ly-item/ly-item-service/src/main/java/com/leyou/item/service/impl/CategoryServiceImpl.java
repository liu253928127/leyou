package com.leyou.item.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leyou.item.dao.mapper.CategoryMapper;
import com.leyou.item.entity.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author coofive
 * @version : 1.0.0
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    /**
     * 添加商品分类
     *
     * @param category 商品分类
     * @return boolean
     */
    @Override
    public boolean addCategory(Category category) {
        return this.save(category);
    }

    /**
     * 修改商品分类
     *
     * @param category 商品分类
     * @return boolean
     */
    @Override
    public boolean updateCategory(Category category) {
        return this.updateById(category);
    }

    /**
     * 删除商品分类
     *
     * @param id 商品分类id
     * @return boolean
     */
    @Override
    public boolean deleteCategory(Long id) {
        return this.removeById(id);
    }

    /**
     * 根据商品分类id查询商品分类
     *
     * @param id 商品分类id
     * @return boolean
     */
    @Override
    public Category getCategoryById(Long id) {
        return this.getById(id);
    }

    /**
     * 根据父节点id查询商品分类
     *
     * @param parentId 商品分类父节点id
     * @return List
     */
    @Override
    public List<Category> getCategoryByParentId(Long parentId) {
        return this.list(new QueryWrapper<Category>().lambda().eq(Category::getParentId, parentId));
    }

    /**
     * 根据商品分类ids查询商品分类
     *
     * @param ids 商品分类id
     * @return List
     */
    @Override
    public List<Category> getCategoryByIds(List<Long> ids) {
        return (List<Category>) this.listByIds(ids);
    }

    /**
     * 根据cid查询层级所有商品分类
     *
     * @param cid 商品分类id
     * @return List
     */
    @Override
    public List<Category> getCategoryByCid(Long cid) {
        List<Category> categories = new ArrayList<>();

        // 层级商品分类
        levelCategory(categories, cid);

        Collections.reverse(categories);
        return categories;
    }

    /**
     * 层级商品分类
     *
     * @param categories 商品分类list
     * @param cid 商品分类id
     */
    private void levelCategory(List<Category> categories, Long cid) {
        Category category = this.getById(cid);
        if (category != null) {
            categories.add(category);
            levelCategory(categories, category.getParentId());
        }

    }
}
