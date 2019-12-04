import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IUnidadeNegocios, defaultValue } from 'app/shared/model/unidade-negocios.model';

export const ACTION_TYPES = {
  SEARCH_UNIDADENEGOCIOS: 'unidadeNegocios/SEARCH_UNIDADENEGOCIOS',
  FETCH_UNIDADENEGOCIOS_LIST: 'unidadeNegocios/FETCH_UNIDADENEGOCIOS_LIST',
  FETCH_UNIDADENEGOCIOS: 'unidadeNegocios/FETCH_UNIDADENEGOCIOS',
  CREATE_UNIDADENEGOCIOS: 'unidadeNegocios/CREATE_UNIDADENEGOCIOS',
  UPDATE_UNIDADENEGOCIOS: 'unidadeNegocios/UPDATE_UNIDADENEGOCIOS',
  DELETE_UNIDADENEGOCIOS: 'unidadeNegocios/DELETE_UNIDADENEGOCIOS',
  RESET: 'unidadeNegocios/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IUnidadeNegocios>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type UnidadeNegociosState = Readonly<typeof initialState>;

// Reducer

export default (state: UnidadeNegociosState = initialState, action): UnidadeNegociosState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_UNIDADENEGOCIOS):
    case REQUEST(ACTION_TYPES.FETCH_UNIDADENEGOCIOS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_UNIDADENEGOCIOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_UNIDADENEGOCIOS):
    case REQUEST(ACTION_TYPES.UPDATE_UNIDADENEGOCIOS):
    case REQUEST(ACTION_TYPES.DELETE_UNIDADENEGOCIOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_UNIDADENEGOCIOS):
    case FAILURE(ACTION_TYPES.FETCH_UNIDADENEGOCIOS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_UNIDADENEGOCIOS):
    case FAILURE(ACTION_TYPES.CREATE_UNIDADENEGOCIOS):
    case FAILURE(ACTION_TYPES.UPDATE_UNIDADENEGOCIOS):
    case FAILURE(ACTION_TYPES.DELETE_UNIDADENEGOCIOS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_UNIDADENEGOCIOS):
    case SUCCESS(ACTION_TYPES.FETCH_UNIDADENEGOCIOS_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_UNIDADENEGOCIOS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_UNIDADENEGOCIOS):
    case SUCCESS(ACTION_TYPES.UPDATE_UNIDADENEGOCIOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_UNIDADENEGOCIOS):
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

const apiUrl = 'api/unidade-negocios';
const apiSearchUrl = 'api/_search/unidade-negocios';

// Actions

export const getSearchEntities: ICrudSearchAction<IUnidadeNegocios> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_UNIDADENEGOCIOS,
  payload: axios.get<IUnidadeNegocios>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IUnidadeNegocios> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_UNIDADENEGOCIOS_LIST,
  payload: axios.get<IUnidadeNegocios>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IUnidadeNegocios> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_UNIDADENEGOCIOS,
    payload: axios.get<IUnidadeNegocios>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IUnidadeNegocios> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_UNIDADENEGOCIOS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IUnidadeNegocios> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_UNIDADENEGOCIOS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IUnidadeNegocios> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_UNIDADENEGOCIOS,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
