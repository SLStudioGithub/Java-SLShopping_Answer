package com.example.user;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.example.entity.User;

@SpringBootTest
class UserServiceTest {

    /** テスト対象クラス */
    private final UserService target;
    
    @Autowired
    public UserServiceTest(UserService userService) {
        this.target = userService;
    }

    /**
    * 概要 メールアドレス・管理者名のパラメーター化テスト<br>
    * 条件1 メールアドレスが10文字かつ管理者名が1文字の場合<br>
    * 条件2 メールアドレスが50文字かつ管理者名が10文字の場合<br>
    * 条件3 メールアドレスが50文字かつ管理者名が1文字の場合<br>
    * 条件4 メールアドレスが10文字かつ管理者名が10文字の場合<br>
    * 結果 trueを返すこと
    */
    @ParameterizedTest
    @CsvSource({
            "ああああああああああ, い",
            "ああああああああああああああああああああああああああああああああああああああああああああああああああ, いいいいいいいいいい",
            "ああああああああああああああああああああああああああああああああああああああああああああああああああ, い",
            "ああああああああああ, いいいいいいいいいい",
    })
    void parameterTestTrue(String email, String name) {
        assertThat(target.isValid(email, name)).isTrue();
    }

    /**
    * 概要 メールアドレス・管理者名のパラメーター化テスト<br>
    * 条件1 メールアドレスが0文字かつ管理者名が0文字の場合<br>
    * 条件2 メールアドレスが51文字かつ管理者名が11文字の場合<br>
    * 条件3 メールアドレスが50文字かつ管理者名が0文字の場合<br>
    * 条件4 メールアドレスが0文字かつ管理者名が10文字の場合<br>
    * 条件5 メールアドレスが50文字かつ管理者名が11文字の場合<br>
    * 条件6 メールアドレスが51文字かつ管理者名が10文字の場合<br>
    * 結果 falseを返すこと
    */
    @ParameterizedTest
    @CsvSource({
            "'', ''",
            "あああああああああああああああああああああああああああああああああああああああああああああああああああ, いいいいいいいいいいい",
            "ああああああああああああああああああああああああああああああああああああああああああああああああああ, ''",
            "'', いいいいいいいいいい",
            "ああああああああああああああああああああああああああああああああああああああああああああああああああ, いいいいいいいいいいい",
            "あああああああああああああああああああああああああああああああああああああああああああああああああああ, いいいいいいいいいい",
    })
    void parameterTestFalse(String email, String name) {
        assertThat(target.isValid(email, name)).isFalse();
    }   
    
    /**
    * 概要 管理者メールアドレスの重複チェック<br>
    * 条件 管理者メールアドレスが重複していない場合<br>
    * 結果 trueを返すこと
    */
    @Test
    void 管理者メールアドレスが重複していない場合trueを返すこと() {
        User user = new User("abc@example.com");
        assertThat(target.checkUnique(user)).isTrue();
    }
    
    /**
    * 概要 管理者メールアドレスの重複チェック<br>
    * 条件 管理者メールアドレスが重複する場合<br>
    * 結果 falseを返すこと
    */
    @Test
    void 管理者メールアドレスが重複する場合falseを返すこと() {
        User user = new User("admin@example.com");
        assertThat(target.checkUnique(user)).isFalse();
    }
    
    /**
    * 概要 管理者情報の取得<br>
    * 条件 指定した管理者IDに対応する管理者情報が存在する場合<br>
    * 結果 例外が発生しないこと
    */
    @Test
    void 管理者情報が存在する場合例外が発生しないこと() {
        assertThatCode(() -> {
            target.get(1L);
        }).doesNotThrowAnyException();
    }
    
    /**
    * 概要 管理者情報の取得<br>
    * 条件 指定した管理者IDに対応する管理者情報が存在しない場合<br>
    * 結果 例外が発生すること
    */
    @Test
    void 管理者情報が存在しない場合例外が発生すること() {
        assertThatThrownBy(() -> {
            target.get(1000L);
        })
        .isInstanceOf(NotFoundException.class);
    }

}
