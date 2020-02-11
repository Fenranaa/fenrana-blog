const baseUrl = "http://localhost:8081";

export default {
  articles: baseUrl + "/admin/articles",
  categorys: baseUrl + "/admin/categorys",
  uploadFile: baseUrl + "/admin/file/upload",
  tags: baseUrl + "/admin/tags",
  fileImages: baseUrl + "/admin/file/images",
  addArticle: baseUrl + "/admin/addArticle",
  articlesRecycle(id) {
    return baseUrl + "/admin/articlesRecycle/" + id;
  },
  articleRestore(id) {
    return baseUrl + "/admin/articleRestore/" + id;
  },
  articleDelete(id) {
    return baseUrl + "/admin/articleDelete/" + id;
  }
};
