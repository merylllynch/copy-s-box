import home from '../components/home.vue'
import login from '../components/login.vue'
import VueRouter from 'vue-router'


const routes = [
    { 
        path: '/login', 
        component: login ,
        name:'login'
    },
    { 
        path: '/home', 
        component: home ,
        name:'home'
    },
    {
        path:'*',
        name:'any',
        redirect:'/login'
    },
  ]

  const router = new VueRouter({
    routes // (缩写) 相当于 routes: routes
  })
  //路由守卫
  router.beforeEach((to,from,next) => {
    //如果跳转的页面不是登录页面
    if (to.path != '/login') {
        // console.log(sessionStorage.getItem("isLogined"));
        //读取会话存储空间的isLogined来判断是否登录
        if (sessionStorage.getItem("isLogined")==1) {
            next()
        
        }else{      //未登录则跳转到登录界面
            alert('请先登录');           
            next('/login');            
        }
    }else{
        next()
    }
  })

  export default router
