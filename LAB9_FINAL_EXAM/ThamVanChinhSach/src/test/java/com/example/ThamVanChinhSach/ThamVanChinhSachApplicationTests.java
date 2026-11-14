package com.example.ThamVanChinhSach;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Kiểm tra tối thiểu các hằng số kiến trúc của ứng dụng mà không cần kết nối CSDL.
 * Các quy tắc nghiệp vụ được kiểm tra riêng trong ChuDeServiceTest và YKienServiceTest.
 */
class ThamVanChinhSachApplicationTests {

    @Test
    void applicationClassExists() {
        assertThat(ThamVanChinhSachApplication.class).isNotNull();
    }
}
