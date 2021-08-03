public class Main {

    /** 当前页码数 */
    private int currentPage = 1;

    /** 需要分页的长字符串 */
    private String text;

    /** 每页显示字符数, 默认为 200 */
    private int sizePerPage = 200;

    /** 总页数 */
    private int totalPage;

    /**
     * 返回当前页的字符串.
     *
     * @return
     */
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

    /**
     * @return Returns the 当前页码数.
     */
    public int getCurrentPage() {
        if (currentPage <= 0)
            currentPage = 1;

        return currentPage;
    }

    /**
     * 设置当前页码, 从 1 开始.
     *
     * @param currentPage
     *            The 当前页码数 to set.
     */
    public void setCurrentPage(int currentPage) {
        if (currentPage <= 0) {
            currentPage = 1;
        }
        this.currentPage = currentPage;
    }

    /**
     * @return Returns the 总页码数, 如果没有数据, 就返回 1.
     */
    public int getTotalPage() {
        if (getText() == null)
            totalPage = 0;
        totalPage = (int) Math
                .ceil(1.0 * getText().length() / getSizePerPage()); // 总页面数

        if (totalPage == 0)
            totalPage = 1;

        return totalPage;
    }


    /**
     * @return Returns the 每页显示字符数.
     */
    public int getSizePerPage() {
        return sizePerPage;
    }

    /**
     * @param sizePerPage
     *            The 每页显示字符数 to set.
     */
    public void setSizePerPage(int sizePerPage) {
        this.sizePerPage = sizePerPage;
    }

    /**
     * @return Returns the 需要分页的长字符串.
     */
    public String getText() {
        return text;
    }

    /**
     * @param text
     *            The 需要分页的长字符串 to set.
     */
    public void setText(String text) {
        this.text = text;
    }

    public static void main(String[] args) {
        Main pager = new Main();
        pager.setText("哈哈哈哈哈咯");
        pager.setSizePerPage(5);
        pager.setCurrentPage(1);

        System.out.println(pager.getTotalPage());
        System.out.println(pager.getCurrentPagedText());
    }
}