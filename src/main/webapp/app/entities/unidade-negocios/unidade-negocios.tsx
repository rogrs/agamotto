import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './unidade-negocios.reducer';
import { IUnidadeNegocios } from 'app/shared/model/unidade-negocios.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IUnidadeNegociosProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const UnidadeNegocios = (props: IUnidadeNegociosProps) => {
  const [search, setSearch] = useState('');

  useEffect(() => {
    props.getEntities();
  }, []);

  const startSearching = () => {
    if (search) {
      props.getSearchEntities(search);
    }
  };

  const clear = () => {
    setSearch('');
    props.getEntities();
  };

  const handleSearch = event => setSearch(event.target.value);

  const { unidadeNegociosList, match, loading } = props;
  return (
    <div>
      <h2 id="unidade-negocios-heading">
        <Translate contentKey="agamottoApp.unidadeNegocios.home.title">Unidade Negocios</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="agamottoApp.unidadeNegocios.home.createLabel">Create new Unidade Negocios</Translate>
        </Link>
      </h2>
      <Row>
        <Col sm="12">
          <AvForm onSubmit={startSearching}>
            <AvGroup>
              <InputGroup>
                <AvInput
                  type="text"
                  name="search"
                  value={search}
                  onChange={handleSearch}
                  placeholder={translate('agamottoApp.unidadeNegocios.home.search')}
                />
                <Button className="input-group-addon">
                  <FontAwesomeIcon icon="search" />
                </Button>
                <Button type="reset" className="input-group-addon" onClick={clear}>
                  <FontAwesomeIcon icon="trash" />
                </Button>
              </InputGroup>
            </AvGroup>
          </AvForm>
        </Col>
      </Row>
      <div className="table-responsive">
        {unidadeNegociosList && unidadeNegociosList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="agamottoApp.unidadeNegocios.razaoSocial">Razao Social</Translate>
                </th>
                <th>
                  <Translate contentKey="agamottoApp.unidadeNegocios.nomeEmpresa">Nome Empresa</Translate>
                </th>
                <th>
                  <Translate contentKey="agamottoApp.unidadeNegocios.cnpj">Cnpj</Translate>
                </th>
                <th>
                  <Translate contentKey="agamottoApp.unidadeNegocios.inscricaoEstadual">Inscricao Estadual</Translate>
                </th>
                <th>
                  <Translate contentKey="agamottoApp.unidadeNegocios.empregadora">Empregadora</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {unidadeNegociosList.map((unidadeNegocios, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${unidadeNegocios.id}`} color="link" size="sm">
                      {unidadeNegocios.id}
                    </Button>
                  </td>
                  <td>{unidadeNegocios.razaoSocial}</td>
                  <td>{unidadeNegocios.nomeEmpresa}</td>
                  <td>{unidadeNegocios.cnpj}</td>
                  <td>{unidadeNegocios.inscricaoEstadual}</td>
                  <td>
                    <Translate contentKey={`agamottoApp.TipoBoolean.${unidadeNegocios.empregadora}`} />
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${unidadeNegocios.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${unidadeNegocios.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${unidadeNegocios.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="agamottoApp.unidadeNegocios.home.notFound">No Unidade Negocios found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ unidadeNegocios }: IRootState) => ({
  unidadeNegociosList: unidadeNegocios.entities,
  loading: unidadeNegocios.loading
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(UnidadeNegocios);
