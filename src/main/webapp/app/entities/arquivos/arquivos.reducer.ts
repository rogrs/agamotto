import axios from 'axios';
import {
  ICrudSearchAction,
  parseHeaderForLinks,
  loadMoreDataWhenScrolled,
  ICrudGetAction,
  ICrudGetAllAction,
  ICrudPutAction,
  ICrudDeleteAction
} from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IArquivos, defaultValue } from 'app/shared/model/arquivos.model';

export const ACTION_TYPES = {
  SEARCH_ARQUIVOS: 'arquivos/SEARCH_ARQUIVOS',
  FETCH_ARQUIVOS_LIST: 'arquivos/FETCH_ARQUIVOS_LIST',
  FETCH_ARQUIVOS: 'arquivos/FETCH_ARQUIVOS',
  CREATE_ARQUIVOS: 'arquivos/CREATE_ARQUIVOS',
  UPDATE_ARQUIVOS: 'arquivos/UPDATE_ARQUIVOS',
  DELETE_ARQUIVOS: 'arquivos/DELETE_ARQUIVOS',
  RESET: 'arquivos/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IArquivos>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ArquivosState = Readonly<typeof initialState>;

// Reducer

export default (state: ArquivosState = initialState, action): ArquivosState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_ARQUIVOS):
    case REQUEST(ACTION_TYPES.FETCH_ARQUIVOS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_ARQUIVOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_ARQUIVOS):
    case REQUEST(ACTION_TYPES.UPDATE_ARQUIVOS):
    case REQUEST(ACTION_TYPES.DELETE_ARQUIVOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_ARQUIVOS):
    case FAILURE(ACTION_TYPES.FETCH_ARQUIVOS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_ARQUIVOS):
    case FAILURE(ACTION_TYPES.CREATE_ARQUIVOS):
    case FAILURE(ACTION_TYPES.UPDATE_ARQUIVOS):
    case FAILURE(ACTION_TYPES.DELETE_ARQUIVOS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_ARQUIVOS):
    case SUCCESS(ACTION_TYPES.FETCH_ARQUIVOS_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_ARQUIVOS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_ARQUIVOS):
    case SUCCESS(ACTION_TYPES.UPDATE_ARQUIVOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_ARQUIVOS):
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

const apiUrl = 'api/arquivos';
const apiSearchUrl = 'api/_search/arquivos';

// Actions

export const getSearchEntities: ICrudSearchAction<IArquivos> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_ARQUIVOS,
  payload: axios.get<IArquivos>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IArquivos> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_ARQUIVOS_LIST,
    payload: axios.get<IArquivos>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IArquivos> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_ARQUIVOS,
    payload: axios.get<IArquivos>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IArquivos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_ARQUIVOS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IArquivos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_ARQUIVOS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IArquivos> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_ARQUIVOS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
