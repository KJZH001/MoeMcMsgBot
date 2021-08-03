package me.ed333.easyBot.utils;

import com.alibaba.fastjson.JSON;
import me.ed333.easyBot.PlaceHolders;
import me.ed333.easyBot.utils.jsonParse.JSONOnGroupMessage;
import me.ed333.easyBot.utils.jsonParse.MsgGet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.upperlevel.spigot.book.BookUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookCreator {
    // k: bookId 即 messageId, v: book
    public static final HashMap<String, ItemStack> bookMap = new HashMap<>();

    private String bookId;
    private final BookUtil.BookBuilder book = BookUtil.writtenBook();

    /**
     * @param bookId 为 "Book_" + messageId
     */
    public BookCreator(int bookId) {
        this.bookId = "Book_" + bookId;
        book.author("zuo zhe niu bi!").title("zuo zhe niu bi!");
    }

    /**
     * 将转发消息中的内容添加到书本中
     * @param content nodeList
     */
    public void addContent(List<String> content) {
        List<BaseComponent[]> bookPages = new ArrayList<>();

        List<String> nodeList_content = new ArrayList<>();

        // 提取 nodeList 中的文本内容, 添加到 nodeList_content 中
        for (String str : content) {
            JSONOnGroupMessage.ForwardMsgData json = JSON.parseObject(str, JSONOnGroupMessage.ForwardMsgData.class);
            PlaceHolders.forward_name = json.senderName;

            List<String> fwMsgChain = json.messageChain;
            TextComponent multiMsg = new TextComponent();
            multiMsg.addExtra(Messages.getMsg("BookFormat", null));
            multiMsg.addExtra(MsgGet.getMultiMsg(MsgGet.msgType.Group, fwMsgChain, false, true));
            nodeList_content.add(multiMsg.toPlainText());
        }

        String str = replaceChar(nodeList_content);
        PageBuilder pb = new PageBuilder();
        pb.setText(str);
        pb.setSizePerPage(255);
        for (int i = 0; i < pb.getTotalPage(); i++) {
            try {
                pb.setCurrentPage(i + 1);
                bookPages.add(new BookUtil.PageBuilder().add(pb.getCurrentPagedText()).build());
            } catch (Exception e) { e.printStackTrace(); }
        }

        // 将内容添加到书本中
        book.pages(bookPages);
    }

    // 创建一本书
    public void create() { bookMap.put(bookId, book.build()); }

    public static void openBook(String bookId, Player p) { BookUtil.openPlayer(p, getBook(bookId)); }

    public static ItemStack getBook(String bookId) { return bookMap.get(bookId); }

    private String replaceChar(List<String> nodeList_content) {
        StringBuilder sb = new StringBuilder();

        for (String string : nodeList_content) {
            sb.append(string).append("\n");
        }

        return sb.toString();
    }

    /**https://www.iteye.com/blog/zhaoshuo5550-993051*/
    private static class PageBuilder {

        private int currentPage = 1;

        private String text;

        private int sizePerPage = 200;

        private int totalPage;

        public String getCurrentPagedText() {
            try {
                if (getCurrentPage() < getTotalPage()) {
                    return getText().substring(
                            (getCurrentPage() - 1) * getSizePerPage(),
                            getCurrentPage() * getSizePerPage());
                } else if (getTotalPage() > 0) {
                    return getText().substring(
                            (getCurrentPage() - 1) * getSizePerPage());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        public int getCurrentPage() throws Exception {
            if (currentPage <= 0) throw new Exception("CurrentPage cannot be " + currentPage);
            return currentPage;
        }

        public void setCurrentPage(int currentPage) throws Exception {
            if (currentPage <= 0) throw new Exception("CurrentPage cannot be " + currentPage);
            this.currentPage = currentPage;
        }

        public int getTotalPage() {
            if (getText() == null)
                totalPage = 0;
            totalPage = (int) Math
                    .ceil(1.0 * getText().length() / getSizePerPage()); // 总页面数

            if (totalPage == 0)
                totalPage = 1;

            return totalPage;
        }

        public int getSizePerPage() { return sizePerPage; }

        public void setSizePerPage(int sizePerPage) { this.sizePerPage = sizePerPage; }

        public String getText() { return text; }

        public void setText(String text) { this.text = text; }
    }
}
