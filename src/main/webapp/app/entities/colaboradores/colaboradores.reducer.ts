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

import { IColaboradores, defaultValue } from 'app/shared/model/colaboradores.model';

export const ACTION_TYPES = {
  SEARCH_COLABORADORES: 'colaboradores/SEARCH_COLABORADORES',
  FETCH_COLABORADORES_LIST: 'colaboradores/FETCH_COLABORADORES_LIST',
  FETCH_COLABORADORES: 'colaboradores/FETCH_COLABORADORES',
  CREATE_COLABORADORES: 'colaboradores/CREATE_COLABORADORES',
  UPDATE_COLABORADORES: 'colaboradores/UPDATE_COLABORADORES',
  DELETE_COLABORADORES: 'colaboradores/DELETE_COLABORADORES',
  RESET: 'colaboradores/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IColaboradores>,
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type ColaboradoresState = Readonly<typeof initialState>;

// Reducer

export default (state: ColaboradoresState = initialState, action): ColaboradoresState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_COLABORADORES):
    case REQUEST(ACTION_TYPES.FETCH_COLABORADORES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_COLABORADORES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_COLABORADORES):
    case REQUEST(ACTION_TYPES.UPDATE_COLABORADORES):
    case REQUEST(ACTION_TYPES.DELETE_COLABORADORES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_COLABORADORES):
    case FAILURE(ACTION_TYPES.FETCH_COLABORADORES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_COLABORADORES):
    case FAILURE(ACTION_TYPES.CREATE_COLABORADORES):
    case FAILURE(ACTION_TYPES.UPDATE_COLABORADORES):
    case FAILURE(ACTION_TYPES.DELETE_COLABORADORES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_COLABORADORES):
    case SUCCESS(ACTION_TYPES.FETCH_COLABORADORES_LIST): {
      const links = parseHeaderForLinks(action.payload.headers.link);

      return {
        ...state,
        loading: false,
        links,
        entities: loadMoreDataWhenScrolled(state.entities, action.payload.data, links),
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    }
    case SUCCESS(ACTION_TYPES.FETCH_COLABORADORES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_COLABORADORES):
    case SUCCESS(ACTION_TYPES.UPDATE_COLABORADORES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_COLABORADORES):
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

const apiUrl = 'api/colaboradores';
const apiSearchUrl = 'api/_search/colaboradores';

// Actions

export const getSearchEntities: ICrudSearchAction<IColaboradores> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_COLABORADORES,
  payload: axios.get<IColaboradores>(`${apiSearchUrl}?query=${query}${sort ? `&page=${page}&size=${size}&sort=${sort}` : ''}`)
});

export const getEntities: ICrudGetAllAction<IColaboradores> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_COLABORADORES_LIST,
    payload: axios.get<IColaboradores>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IColaboradores> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_COLABORADORES,
    payload: axios.get<IColaboradores>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IColaboradores> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_COLABORADORES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const updateEntity: ICrudPutAction<IColaboradores> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_COLABORADORES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IColaboradores> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_COLABORADORES,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
