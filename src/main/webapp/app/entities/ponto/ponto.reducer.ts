import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPonto, defaultValue } from 'app/shared/model/ponto.model';

export const ACTION_TYPES = {
  SEARCH_PONTOS: 'ponto/SEARCH_PONTOS',
  FETCH_PONTO_LIST: 'ponto/FETCH_PONTO_LIST',
  FETCH_PONTO: 'ponto/FETCH_PONTO',
  CREATE_PONTO: 'ponto/CREATE_PONTO',
  UPDATE_PONTO: 'ponto/UPDATE_PONTO',
  DELETE_PONTO: 'ponto/DELETE_PONTO',
  RESET: 'ponto/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPonto>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PontoState = Readonly<typeof initialState>;

// Reducer

export default (state: PontoState = initialState, action): PontoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_PONTOS):
    case REQUEST(ACTION_TYPES.FETCH_PONTO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PONTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PONTO):
    case REQUEST(ACTION_TYPES.UPDATE_PONTO):
    case REQUEST(ACTION_TYPES.DELETE_PONTO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_PONTOS):
    case FAILURE(ACTION_TYPES.FETCH_PONTO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PONTO):
    case FAILURE(ACTION_TYPES.CREATE_PONTO):
    case FAILURE(ACTION_TYPES.UPDATE_PONTO):
    case FAILURE(ACTION_TYPES.DELETE_PONTO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_PONTOS):
    case SUCCESS(ACTION_TYPES.FETCH_PONTO_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PONTO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PONTO):
    case SUCCESS(ACTION_TYPES.UPDATE_PONTO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PONTO):
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

const apiUrl = 'api/pontos';
const apiSearchUrl = 'api/_search/pontos';

// Actions

export const getSearchEntities: ICrudSearchAction<IPonto> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_PONTOS,
  payload: axios.get<IPonto>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IPonto> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PONTO_LIST,
  payload: axios.get<IPonto>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPonto> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PONTO,
    payload: axios.get<IPonto>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPonto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PONTO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPonto> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PONTO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPonto> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PONTO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
