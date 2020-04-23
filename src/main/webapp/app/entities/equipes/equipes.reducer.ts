import axios from 'axios';
import { ICrudSearchAction, ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IEquipes, defaultValue } from 'app/shared/model/equipes.model';

export const ACTION_TYPES = {
  SEARCH_EQUIPES: 'equipes/SEARCH_EQUIPES',
  FETCH_EQUIPES_LIST: 'equipes/FETCH_EQUIPES_LIST',
  FETCH_EQUIPES: 'equipes/FETCH_EQUIPES',
  CREATE_EQUIPES: 'equipes/CREATE_EQUIPES',
  UPDATE_EQUIPES: 'equipes/UPDATE_EQUIPES',
  DELETE_EQUIPES: 'equipes/DELETE_EQUIPES',
  RESET: 'equipes/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IEquipes>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type EquipesState = Readonly<typeof initialState>;

// Reducer

export default (state: EquipesState = initialState, action): EquipesState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.SEARCH_EQUIPES):
    case REQUEST(ACTION_TYPES.FETCH_EQUIPES_LIST):
    case REQUEST(ACTION_TYPES.FETCH_EQUIPES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_EQUIPES):
    case REQUEST(ACTION_TYPES.UPDATE_EQUIPES):
    case REQUEST(ACTION_TYPES.DELETE_EQUIPES):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.SEARCH_EQUIPES):
    case FAILURE(ACTION_TYPES.FETCH_EQUIPES_LIST):
    case FAILURE(ACTION_TYPES.FETCH_EQUIPES):
    case FAILURE(ACTION_TYPES.CREATE_EQUIPES):
    case FAILURE(ACTION_TYPES.UPDATE_EQUIPES):
    case FAILURE(ACTION_TYPES.DELETE_EQUIPES):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.SEARCH_EQUIPES):
    case SUCCESS(ACTION_TYPES.FETCH_EQUIPES_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_EQUIPES):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_EQUIPES):
    case SUCCESS(ACTION_TYPES.UPDATE_EQUIPES):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_EQUIPES):
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

const apiUrl = 'api/equipes';
const apiSearchUrl = 'api/_search/equipes';

// Actions

export const getSearchEntities: ICrudSearchAction<IEquipes> = (query, page, size, sort) => ({
  type: ACTION_TYPES.SEARCH_EQUIPES,
  payload: axios.get<IEquipes>(`${apiSearchUrl}?query=${query}`)
});

export const getEntities: ICrudGetAllAction<IEquipes> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_EQUIPES_LIST,
  payload: axios.get<IEquipes>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IEquipes> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_EQUIPES,
    payload: axios.get<IEquipes>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IEquipes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_EQUIPES,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IEquipes> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_EQUIPES,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  return result;
};

export const deleteEntity: ICrudDeleteAction<IEquipes> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_EQUIPES,
    payload: axios.delete(requestUrl)
  });
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
