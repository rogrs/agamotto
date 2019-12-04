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

import { ILinhasArquivos, defaultValue } from 'app/shared/model/linhas-arquivos.model';

export const ACTION_TYPES = {
  SEARCH_LINHASARQUIVOS: 'linhasArquivos/SEARCH_LINHASARQUIVOS',
  FETCH_LINHASARQUIVOS_LIST: 'linhasArquivos/FETCH_LINHASARQUIVOS_LIST',
  FETCH_LINHASARQUIVOS: 'linhasArquivos/FETCH_LINHASARQUIVOS',
  CREATE_LINHASARQUIVOS: 'linhasArquivos/CREATE_LINHASARQUIVOS',
  UPDATE_LINHASARQUIVOS: 'linhasArquivos/UPDATE_LINHASARQUIVOS',
  DELETE_LINHASARQUIVOS: 'linhasArquivos/DELETE_LINHASARQUIVOS',
  RESET: 'linhasArquivos/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ILinhasArquivos>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type LinhasArquivosState = Readonly<typeof initialState>;

// Reducer

export default (state: LinhasArquivosState = initialState, action): LinhasArquivosState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_LINHASARQUIVOS):
    case REQUEST(ACTION_TYPES.FETCH_LINHASARQUIVOS_LIST):
    case REQUEST(ACTION_TYPES.FETCH_LINHASARQUIVOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_LINHASARQUIVOS):
    case REQUEST(ACTION_TYPES.UPDATE_LINHASARQUIVOS):
    case REQUEST(ACTION_TYPES.DELETE_LINHASARQUIVOS):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_LINHASARQUIVOS):
    case FAILURE(ACTION_TYPES.FETCH_LINHASARQUIVOS_LIST):
    case FAILURE(ACTION_TYPES.FETCH_LINHASARQUIVOS):
    case FAILURE(ACTION_TYPES.CREATE_LINHASARQUIVOS):
    case FAILURE(ACTION_TYPES.UPDATE_LINHASARQUIVOS):
    case FAILURE(ACTION_TYPES.DELETE_LINHASARQUIVOS):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_LINHASARQUIVOS):
    case SUCCESS(ACTION_TYPES.FETCH_LINHASARQUIVOS_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_LINHASARQUIVOS):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_LINHASARQUIVOS):
    case SUCCESS(ACTION_TYPES.UPDATE_LINHASARQUIVOS):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_LINHASARQUIVOS):
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

const apiUrl = 'api/linhas-arquivos';
const apiSearchUrl = 'api/_search/linhas-arquivos';

// Actions

export const getSearchEntities: ICrudSearchAction<ILinhasArquivos> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_LINHASARQUIVOS,
  payload: axios.get<ILinhasArquivos>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<ILinhasArquivos> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_LINHASARQUIVOS_LIST,
    payload: axios.get<ILinhasArquivos>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ILinhasArquivos> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_LINHASARQUIVOS,
    payload: axios.get<ILinhasArquivos>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ILinhasArquivos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_LINHASARQUIVOS,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<ILinhasArquivos> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_LINHASARQUIVOS,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<ILinhasArquivos> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_LINHASARQUIVOS,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
