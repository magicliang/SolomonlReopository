/**
 * Created by magiliang on 2016/8/5.
 */
// 这个actions 是用来在各个component之间分发的，这些大写的event，都是针对store里的mutation
export const addTodo = makeAction('ADD_TODO')
export const deleteTodo = makeAction('DELETE_TODO')
export const toggleTodo = makeAction('TOGGLE_TODO')
export const editTodo = makeAction('EDIT_TODO')
export const toggleAll = makeAction('TOGGLE_ALL')
export const clearCompleted = makeAction('CLEAR_COMPLETED')
export const getThingsDone = makeAction('GET_THINGS_DONE')

function makeAction(type){
  return ({ dispatch }, type) => dispatch(type, ...args)
}
