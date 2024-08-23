import request from '@/utils/request'

export function getDepartment(data) {
  return request({
    url: '/laboratory/department',
    method: 'get',
    params: { data }
  })
}
export function newDepartment(data) {
  return request({
    url: '/laboratory/department',
    method: 'post',
    data
  })
}
export function updateDepartment(data) {
  return request({
    url: '/laboratory/department',
    method: 'put',
    data
  })
}

export function deleteDepartment(data) {
  return request({
    url: '/laboratory/department',
    method: 'delete',
    data
  })
}
