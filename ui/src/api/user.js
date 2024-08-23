import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/laboratory/auth/login',
    method: 'post',
    data
  })
}

export function getInfo() {
  return request({
    url: '/laboratory/auth/userInfo',
    method: 'get',
    // params: { token }
  })
}
export function getAllTeacherInfo() {
  return request({
    url: '/laboratory/auth/teacherInfo',
    method: 'get',
    // params: { token }
  })
}
export function getInfoByRole() {
  return request({
    url: '/laboratory/auth/getInfoByRole',
    method: 'get',
  })
}
export function deleteInfo(data) {
  return request({
    url: '/laboratory/auth',
    method: 'delete',
    data
  })
}
export function logout() {
  return request({
    url: '/laboratory/auth/logout',
    method: 'post'
  })
}
export function getDESKey() {
  return request({
    url: '/laboratory/auth/publicKey',
    method: 'get'
  })
}
export function register(data) {
  return request({
    url: '/laboratory/auth/register',
    method: 'post',
    data
  })
}
export function update(data) {
  return request({
    url: '/laboratory/auth/update',
    method: 'put',
    data
  })
}
