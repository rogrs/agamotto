import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IDepartamentos, defaultValue } from 'app/shared/model/departamentos.model';

export const ACTION_TYPES = {
  SEARCH_DEPARTAMENTOS: 'departamentos/SEARCH_DEPARTAMENTOS',
  FETCH_DEPARTAMENTOS_LIST: 'departamentos/FETCH_DEPARTAMENTOS_LIST',
  FETCH_DEPARTAMENTOS: 'departamentos/FETCH_DEPARTAMENTOS',
  CREATE_DEPARTAMENTOS: 'departamentos/CREATE_DEPARTAMENTOS',
  UPDATE_DEPARTAMENTOS: 'departamentos/UPDATE_DEPARTAMENTOS',
  DELETE_DEPARTAMENTOS: 'departamentos/DELETE_DEPARTAMENTOS',
  RESET: 'departamentos/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IDepartamentos>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type DepartamentosState = Readonly<typeof initialState>;

// Reducer

export default (state: DepartamentosState = initialState, action): DepartamentosState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_DEPARTAMENTOS):
    case REQUEST(ACTION_TYPES.FETCH_DEPARTAMENTOS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_DEPARTAMENTOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_DEPARTAMENTOS):
    case REQUEST(ACTION_TYPES.UPDATE_DEPARTAMENTOS):
    case REQUEST(ACTION_TYPES.DELETE_DEPARTAMENTOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_DEPARTAMENTOS):
    case FAILURE(ACTION_TYPES.FETCH_DEPARTAMENTOS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_DEPARTAMENTOS):
    case FAILURE(ACTION_TYPES.CREATE_DEPARTAMENTOS):
    case FAILURE(ACTION_TYPES.UPDATE_DEPARTAMENTOS):
    case FAILURE(ACTION_TYPES.DELETE_DEPARTAMENTOS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_DEPARTAMENTOS):
    case SUCCESS(ACTION_TYPES.FETCH_DEPARTAMENTOS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_DEPARTAMENTOS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_DEPARTAMENTOS):
    case SUCCESS(ACTION_TYPES.UPDATE_DEPARTAMENTOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_DEPARTAMENTOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/departamentos';
const apiSearchUrl = 'api/_search/departamentos';

// Actions

export const getSearchEntities: ICrudSearchAction<IDepartamentos> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_DEPARTAMENTOS,
  payload: axios.get<IDepartamentos>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IDepartamentos> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_DEPARTAMENTOS_LIST,
  payload: axios.get<IDepartamentos>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IDepartamentos> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_DEPARTAMENTOS,
    payload: axios.get<IDepartamentos>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IDepartamentos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_DEPARTAMENTOS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IDepartamentos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_DEPARTAMENTOS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IDepartamentos> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_DEPARTAMENTOS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
