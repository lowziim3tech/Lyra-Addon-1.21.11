# MeTech Auto Sell — Fabric 1.21.11

Trạng thái: source chưa compile / chưa test trong Minecraft hoặc Donut.
Không có JAR cài ngay trong gói này. Test timer độc lập không thay thế test mod.

## Chức năng
- H: mở GUI dùng Screen/ButtonWidget mặc định Minecraft.
- V: bật/tắt tự chặt. Đổi cả hai phím ở Options > Controls > Key Binds > MeTech Auto Sell.
- Khi đã bật: nhấp chặt mỗi 500 ms dù đang mở GUI, inventory, chat hoặc Alt-Tab trên multiplayer.
- Kiểm tra mỗi 120 giây thực bằng đồng hồ monotonic. Nếu bật Look Up và Auto Chop, sửa pitch về -90° (thẳng lên trời), giữ nguyên yaw.
- Chu kỳ đầu bắt đầu lúc bật; không xoay ngay. Hãy tự ngước lên rương trước khi nhấn V.
- Chỉ chặt chest/trapped chest trong tầm tương tác thật khi cầm vật phẩm thuộc tag axes; không tự chọn rìu.
- Ngắt tiến trình đào sau mỗi click; không giữ chuột. Không xác nhận server đã bán thành công.
- Phím H/V dùng khi không mở màn hình, để không kích hoạt lúc gõ chat/đổi keybind. Khi mở GUI MeTech dùng nút Auto Chop để dừng.
- Tắt khi chết hoặc disconnect. Không tự reconnect hoặc tự bật lại khi vào server.
- Tạm tắt pauseOnLostFocus khi chạy và phục hồi khi dừng. Không thay đổi vĩnh viễn cài đặt này.

## Build
1. Cài JDK 21 và Gradle 9.2.1.
2. Mở terminal tại thư mục chứa build.gradle.
3. Chạy `gradle build`.
4. Lấy `build/libs/metech-auto-sell-1.0.0.jar` (không dùng sources.jar).
5. Cài Fabric Loader >=0.18.1 cho Minecraft 1.21.11, thêm Fabric API bản dành cho 1.21.11 và JAR mod vào mods.

Có workflow GitHub Actions: đưa toàn bộ nội dung thư mục này vào root repository, chạy Build Fabric mod, tải artifact. Workflow chưa được chạy ở môi trường tạo source.

## Test trước khi treo
1. Dùng server test có cơ chế sell axe tương tự; không thử với rương quý trong creative (click có thể phá ngay).
2. Cầm sell axe, ngước lên rương, bật V. Kiểm tra bán trên server, không chỉ animation.
3. Mở inventory, GUI H, chat và Alt-Tab: xác nhận server vẫn nhận click.
4. Lệch camera: sau 120 giây từ lần kiểm tra trước, pitch phải thành -90°.
5. Tắt Look Up: camera không bị sửa; tắt Auto Chop: cả click và sửa góc đều dừng.
6. Đổi H/V trong Controls, thử lại sau khi khởi động game.
7. Bỏ rìu / rời rương / disconnect / chết: xác nhận không chặt nhầm.

Máy phải tiếp tục chạy Minecraft và có mạng. Sleep, đóng game, server restart/kick hoặc mod giảm tick nền có thể làm ngừng hoạt động; không bảo đảm treo liên tục 24/7. Màn hình pause singleplayer không được hỗ trợ. Chỉ dùng khi server cho phép automation.

## Nguồn đối chiếu
- https://fabricmc.net/2025/12/05/12111.html
- https://maven.fabricmc.net/docs/yarn-1.21.11+build.4/

## Test timer độc lập
`javac -d test-out src/main/java/vn/lowzii/metech/Interval.java tests/IntervalTest.java`

`java -cp test-out IntervalTest`
