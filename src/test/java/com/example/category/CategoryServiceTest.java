package com.example.category;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.example.entity.Category;

@SpringBootTest
class CategoryServiceTest {

    /** テスト対象クラス */
    private final CategoryService target;
    
    @Autowired
    public CategoryServiceTest(CategoryService categoryService) {
        this.target = categoryService;
    }

   
    /**
    * 概要 カテゴリー名の入力チェック<br>
    * 条件 カテゴリー名が1文字の場合<br>
    * 結果 trueを返すこと
    */
    @Test
    void カテゴリー名が1文字の場合trueを返すこと() {
        Category category = new Category("あ");
        assertThat(target.isValid(category)).isTrue();
    }
    
    /**
     * 概要 カテゴリー名の入力チェック<br>
     * 条件 カテゴリー名が32文字の場合<br>
     * 結果 trueを返すこと
     */
    @Test
    void カテゴリー名が32文字の場合trueを返すこと() {
        Category category = new Category(
                "ああああああああああ"
              + "ああああああああああ"
              + "ああああああああああ"
              + "ああ"
          );
        assertThat(target.isValid(category)).isTrue();
    }

    /**
    * 概要 カテゴリー名の入力チェック<br>
    * 条件 カテゴリー名が0文字の場合<br>
    * 結果 falseを返すこと
    */
    @Test
    void カテゴリー名が0文字の場合falseを返すこと() {
        Category category = new Category("");
        assertThat(target.isValid(category)).isFalse();
    }
    
    /**
    * 概要 カテゴリー名の入力チェック<br>
    * 条件 カテゴリー名が33文字の場合<br>
    * 結果 falseを返すこと
    */
    @Test
    void カテゴリー名が33文字の場合falseを返すこと() {
        Category category = new Category(
                      "ああああああああああ"
                    + "ああああああああああ"
                    + "ああああああああああ"
                    + "あああ"
                );
        assertThat(target.isValid(category)).isFalse();
    }
    
    /**
    * 概要 カテゴリー名の重複チェック<br>
    * 条件 カテゴリー名が重複していない場合<br>
    * 結果 trueを返すこと
    */
    @Test
    void カテゴリー名が重複していない場合trueを返すこと() {
        Category category = new Category("あいうえお");
        assertThat(target.checkUnique(category)).isTrue();
    }
    
    /**
    * 概要 カテゴリー名の重複チェック<br>
    * 条件 カテゴリー名が重複する場合<br>
    * 結果 falseを返すこと
    */
    @Test
    void カテゴリー名が重複する場合falseを返すこと() {
        Category category = new Category("カテゴリーA");
        assertThat(target.checkUnique(category)).isFalse();
    }
    
    /**
    * 概要 カテゴリー情報の取得<br>
    * 条件 指定したカテゴリーIDに対応するカテゴリー情報が存在する場合<br>
    * 結果 例外が発生しないこと
    */
    @Test
    void カテゴリー情報が存在する場合例外が発生しないこと() {
        assertThatCode(() -> {
            target.get(1L);
        }).doesNotThrowAnyException();
    }
    
    /**
    * 概要 カテゴリー情報の取得<br>
    * 条件 指定したカテゴリーIDに対応するカテゴリー情報が存在しない場合<br>
    * 結果 例外が発生すること
    */
    @Test
    void カテゴリー情報が存在しない場合例外が発生すること() {
        assertThatThrownBy(() -> {
            target.get(1000L);
        })
        .isInstanceOf(NotFoundException.class);
    }

}
