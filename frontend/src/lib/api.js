import axios from 'axios';
export const api=axios.create({baseURL:import.meta.env.VITE_API_BASE_URL||'http://localhost:8080/api'});
api.interceptors.request.use(config=>{const token=localStorage.getItem('fintrack_token');if(token)config.headers.Authorization=`Bearer ${token}`;return config;});
api.interceptors.response.use(r=>r,e=>{if(e.response?.status===401){localStorage.removeItem('fintrack_token');localStorage.removeItem('fintrack_user');if(!location.pathname.startsWith('/login')&&!location.pathname.startsWith('/register'))location.href='/login';}return Promise.reject(e);});
export function message(error){return error?.response?.data?.error||error?.response?.data?.message||error?.message||'Something went wrong';}
