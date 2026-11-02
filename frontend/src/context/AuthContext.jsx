import {createContext,useContext,useMemo,useState} from 'react';import {api} from '../lib/api';
const AuthContext=createContext(null);
export function AuthProvider({children}){const [user,setUser]=useState(()=>{try{return JSON.parse(localStorage.getItem('fintrack_user')||'null')}catch{return null}});const [token,setToken]=useState(()=>localStorage.getItem('fintrack_token'));
const accept=data=>{localStorage.setItem('fintrack_token',data.token);localStorage.setItem('fintrack_user',JSON.stringify(data.user));setToken(data.token);setUser(data.user)};
const login=async loginData=>accept((await api.post('/auth/login',loginData)).data);const register=async data=>accept((await api.post('/auth/register',data)).data);const logout=()=>{localStorage.removeItem('fintrack_token');localStorage.removeItem('fintrack_user');setToken(null);setUser(null)};
const value=useMemo(()=>({user,token,login,register,logout}),[user,token]);return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>}
export const useAuth=()=>useContext(AuthContext);
