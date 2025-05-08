import { createRouter, createWebHistory } from 'vue-router';

const routes = [
  {
    path: '/admin',
    component: () => import('@layout/dashboard.vue'),
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/pages/admin/dashboard.vue')
      },
      {
        path: 'phones-list',
        name: 'PhonesList',
        component: () => import('@/pages/admin/phones-list.vue')
      }
    ]
  },
  {
    path: '',
    component: () => import('@layout/default.vue'),
    children: [
      {
        path: '',
        name: 'Home',
        component: () => import('@pages/user/home.vue')
      },
      {
        path: 'brands',
        name: 'Brands',
        component: () => import('@pages/user/brands.vue')
      },
      {
        path: 'phone/:name',
        name: 'Phone',
        component: () => import('@pages/user/detail-phone.vue')
      },
      {
        path: 'search',
        name: 'Search',
        component: () => import('@/pages/user/search.vue')
      },
      {
        path: 'compare',
        name: 'Compare',
        component: () => import('@/pages/user/compare.vue')
      }
    ]
  },
  {
    path: '/',
    component: () => import('@layout/blank.vue'),
    children: [
      {
        path: 'sign-in',
        name: 'Sign in',
        component: () => import('@/pages/user/sign-in.vue')
      },
      {
        path: 'sign-up',
        name: 'Sign up',
        component: () => import('@/pages/user/sign-up.vue')
      },
      {
        path: 'forgot-password',
        name: 'Forgot password',
        component: () => import('@/pages/user/forgot-password.vue')
      }
    ]
  }
];

export const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return {
        savedPosition
      };
    } else {
      return { top: 0, behavior: 'smooth' };
    }
  }
});

const whiteListByPath = ['/sign-in'];

const whiteListByName = ['Home', 'Brands', 'Phone', 'Search', 'Compare'];

const isWhiteList = (to) => {
  return whiteListByPath.includes(to.path) || whiteListByName.includes(to.name);
};

const navigationGuard = (router) => {
  router.beforeEach(async (to, _from) => {
    if (!localStorage.getItem('token')) {
      if (isWhiteList(to)) return true;
      return '/sign-in';
    }

    if (to.path === '/sign-in') return '/';
  });
};

navigationGuard(router);
