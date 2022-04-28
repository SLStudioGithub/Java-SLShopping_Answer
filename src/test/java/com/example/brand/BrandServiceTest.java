package com.example.brand;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.example.entity.Brand;

@SpringBootTest
class BrandServiceTest {

    /** テスト対象クラス */
    private final BrandService target;
    
    @Autowired
    public BrandServiceTest(BrandService brandService) {
        this.target = brandService;
    }

   
    /**
    * 概要 ブランド名の入力チェック<br>
    * 条件 ブランド名が1文字の場合<br>
    * 結果 trueを返すこと
    */
    @Test
    void ブランド名が1文字の場合trueを返すこと() {
        Brand brand = new Brand("あ");
        assertThat(target.isValid(brand)).isTrue();
    }
    
    /**
     * 概要 ブランド名の入力チェック<br>
     * 条件 ブランド名が10文字の場合<br>
     * 結果 trueを返すこと
     */
    @Test
    void ブランド名が10文字の場合trueを返すこと() {
        Brand brand = new Brand("ああああああああああ");
        assertThat(target.isValid(brand)).isTrue();
    }

    /**
    * 概要 ブランド名の入力チェック<br>
    * 条件 ブランド名が0文字の場合<br>
    * 結果 falseを返すこと
    */
    @Test
    void ブランド名が0文字の場合falseを返すこと() {
        Brand brand = new Brand("");
        assertThat(target.isValid(brand)).isFalse();
    }
    
    /**
    * 概要 ブランド名の入力チェック<br>
    * 条件 ブランド名が11文字の場合<br>
    * 結果 falseを返すこと
    */
    @Test
    void ブランド名が11文字の場合falseを返すこと() {
        Brand brand = new Brand("あああああああああああ");
        assertThat(target.isValid(brand)).isFalse();
    }
    
    /**
    * 概要 ブランド名の重複チェック<br>
    * 条件 ブランド名が重複していない場合<br>
    * 結果 trueを返すこと
    */
    @Test
    void ブランド名が重複していない場合trueを返すこと() {
        Brand brand = new Brand("あいうえお");
        assertThat(target.checkUnique(brand)).isTrue();
    }
    
    /**
    * 概要 ブランド名の重複チェック<br>
    * 条件 ブランド名が重複する場合<br>
    * 結果 falseを返すこと
    */
    @Test
    void ブランド名が重複する場合falseを返すこと() {
        Brand brand = new Brand("ブランドA");
        assertThat(target.checkUnique(brand)).isFalse();
    }
    
    /**
    * 概要 ブランド情報の取得<br>
    * 条件 指定したブランドIDに対応するブランド情報が存在する場合<br>
    * 結果 例外が発生しないこと
    */
    @Test
    void ブランド情報が存在する場合例外が発生しないこと() {
        assertThatCode(() -> {
            target.get(1L);
        }).doesNotThrowAnyException();
    }
    
    /**
    * 概要 ブランド情報の取得<br>
    * 条件 指定したブランドIDに対応するブランド情報が存在しない場合<br>
    * 結果 例外が発生すること
    */
    @Test
    void ブランド情報が存在しない場合例外が発生すること() {
        assertThatThrownBy(() -> {
            target.get(1000L);
        })
        .isInstanceOf(NotFoundException.class);
    }

}
