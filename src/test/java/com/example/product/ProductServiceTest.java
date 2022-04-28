package com.example.product;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.example.entity.Product;

@SpringBootTest
class ProductServiceTest {

    /** テスト対象クラス */
    private final ProductService target;
    
    @Autowired
    public ProductServiceTest(ProductService productService) {
        this.target = productService;
    }

   
    /**
    * 概要 商品名・商品説明のパラメーター化テスト<br>
    * 条件1 商品名が1文字かつ商品説明が1文字の場合<br>
    * 条件2 商品名が10文字かつ商品説明が50文字の場合<br>
    * 条件3 商品名が10文字かつ商品説明が1文字の場合<br>
    * 条件4 商品名が1文字かつ商品説明が50文字の場合<br>
    * 結果 trueを返すこと
    */
    @ParameterizedTest
    @CsvSource({
            "あ, い",
            "ああああああああああ, いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい",
            "ああああああああああ, い",
            "あ, いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい"
    })
    void parameterTestTrue(String name, String description) {
        assertThat(target.isValid(name, description)).isTrue();
    }

    /**
    * 概要 商品名・商品説明のパラメーター化テスト<br>
    * 条件1 商品名が0文字かつ商品説明が0文字の場合<br>
    * 条件2 商品名が11文字かつ商品説明が51文字の場合<br>
    * 条件3 商品名が10文字かつ商品説明が0文字の場合<br>
    * 条件4 商品名が0文字かつ商品説明が50文字の場合<br>
    * 条件5 商品名が10文字かつ商品説明が51文字の場合<br>
    * 条件6 商品名が11文字かつ商品説明が50文字の場合<br>
    * 結果 falseを返すこと
    */
    @ParameterizedTest
    @CsvSource({
            "'', ''",
            "あああああああああああ, いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい",
            "ああああああああああ, ''",
            "'', いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい",
            "ああああああああああ, いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい",
            "あああああああああああ, いいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいいい"
    })
    void parameterTestFalse(String name, String description) {
        assertThat(target.isValid(name, description)).isFalse();
    }

    /**
    * 概要 商品名の重複チェック<br>
    * 条件 商品名が重複していない場合<br>
    * 結果 trueを返すこと
    */
    @Test
    void 商品名が重複していない場合trueを返すこと() {
        Product product = new Product("あいうえお");
        assertThat(target.checkUnique(product)).isTrue();
    }
    
    /**
    * 概要 商品名の重複チェック<br>
    * 条件 商品名が重複する場合<br>
    * 結果 falseを返すこと
    */
    @Test
    void 商品名が重複する場合falseを返すこと() {
        Product product = new Product("商品A");
        assertThat(target.checkUnique(product)).isFalse();
    }
    
    /**
    * 概要 商品情報の取得<br>
    * 条件 指定した商品IDに対応する商品情報が存在する場合<br>
    * 結果 例外が発生しないこと
    */
    @Test
    void 商品情報が存在する場合例外が発生しないこと() {
        assertThatCode(() -> {
            target.get(1L);
        }).doesNotThrowAnyException();
    }
    
    /**
    * 概要 商品情報の取得<br>
    * 条件 指定した商品IDに対応する商品情報が存在しない場合<br>
    * 結果 例外が発生すること
    */
    @Test
    void 商品情報が存在しない場合例外が発生すること() {
        assertThatThrownBy(() -> {
            target.get(1000L);
        })
        .isInstanceOf(NotFoundException.class);
    }

}
