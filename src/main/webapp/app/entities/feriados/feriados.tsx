import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, InputGroup, Col, Row, Table } from 'reactstrap';
import { AvForm, AvGroup, AvInput } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudSearchAction, ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSearchEntities, getEntities } from './feriados.reducer';
import { IFeriados } from 'app/shared/model/feriados.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IFeriadosProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Feriados = (props: IFeriadosProps) => {
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

  const { feriadosList, match, loading } = props;
  return (
    <div>
      <h2 id="feriados-heading">
        <Translate contentKey="agamottoApp.feriados.home.title">Feriados</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="agamottoApp.feriados.home.createLabel">Create new Feriados</Translate>
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
                  placeholder={translate('agamottoApp.feriados.home.search')}
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
        {feriadosList && feriadosList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="global.field.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="agamottoApp.feriados.nome">Nome</Translate>
                </th>
                <th>
                  <Translate contentKey="agamottoApp.feriados.fixoOuMudaTodoAno">Fixo Ou Muda Todo Ano</Translate>
                </th>
                <th>
                  <Translate contentKey="agamottoApp.feriados.dataFeriado">Data Feriado</Translate>
                </th>
                <th>
                  <Translate contentKey="agamottoApp.feriados.unidadeNegocios">Unidade Negocios</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {feriadosList.map((feriados, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${feriados.id}`} color="link" size="sm">
                      {feriados.id}
                    </Button>
                  </td>
                  <td>{feriados.nome}</td>
                  <td>{feriados.fixoOuMudaTodoAno ? 'true' : 'false'}</td>
                  <td>
                    <TextFormat type="date" value={feriados.dataFeriado} format={APP_LOCAL_DATE_FORMAT} />
                  </td>
                  <td>
                    {feriados.unidadeNegocios ? (
                      <Link to={`unidade-negocios/${feriados.unidadeNegocios.id}`}>{feriados.unidadeNegocios.razaoSocial}</Link>
                    ) : (
                      ''
                    )}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${feriados.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${feriados.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${feriados.id}/delete`} color="danger" size="sm">
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
              <Translate contentKey="agamottoApp.feriados.home.notFound">No Feriados found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ feriados }: IRootState) => ({
  feriadosList: feriados.entities,
  loading: feriados.loading
});

const mapDispatchToProps = {
  getSearchEntities,
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Feriados);
