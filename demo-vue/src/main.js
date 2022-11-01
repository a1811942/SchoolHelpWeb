import { createApp } from 'vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from "./router"
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import App from './App.vue'
import axios from "axios"
const app = createApp(App)
axios.defaults.withCredentials=true;
//设置请求头
axios.defaults.headers.post['Content-Type'] = 'application/json';
app.config.globalProperties.$axios = axios
//全局注册，之后可在其他组件中发送数据，跨域访问
axios.defaults.baseURL = 'http://localhost:8080'
//axios挂载到vue实例
app.config.globalProperties.$axios = axios

//注册图标.
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.use(router) //放在app.mount前面
app.use(ElementPlus)
app.mount("#app");
