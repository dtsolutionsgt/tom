package com.dts.classes.Mantenimientos.Empresa;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.simpleframework.xml.ElementList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class clsBeEmpresaList {
    @ElementList(inline=true,required = false)
    public List<clsBeEmpresa> items = new List<clsBeEmpresa>() {
        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean contains(@Nullable Object o) {
            return false;
        }

        @NonNull
        @Override
        public Iterator<clsBeEmpresa> iterator() {
            return null;
        }

        @Nullable
        @Override
        public Object[] toArray() {
            return new Object[0];
        }

        @Override
        public <T> T[] toArray(@Nullable T[] a) {
            return null;
        }

        @Override
        public boolean add(clsBeEmpresa clsBeEmpresa) {
            return false;
        }

        @Override
        public boolean remove(@Nullable Object o) {
            return false;
        }

        @Override
        public boolean containsAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean addAll(@NonNull Collection<? extends clsBeEmpresa> c) {
            return false;
        }

        @Override
        public boolean addAll(int index, @NonNull Collection<? extends clsBeEmpresa> c) {
            return false;
        }

        @Override
        public boolean removeAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public boolean retainAll(@NonNull Collection<?> c) {
            return false;
        }

        @Override
        public void clear() {

        }

        @Override
        public boolean equals(@Nullable Object o) {
            return false;
        }

        @Override
        public int hashCode() {
            return 0;
        }

        @Override
        public clsBeEmpresa get(int index) {
            return null;
        }

        @Override
        public clsBeEmpresa set(int index, clsBeEmpresa element) {
            return null;
        }

        @Override
        public void add(int index, clsBeEmpresa element) {

        }

        @Override
        public clsBeEmpresa remove(int index) {
            return null;
        }

        @Override
        public int indexOf(@Nullable Object o) {
            return 0;
        }

        @Override
        public int lastIndexOf(@Nullable Object o) {
            return 0;
        }

        @NonNull
        @Override
        public ListIterator<clsBeEmpresa> listIterator() {
            return null;
        }

        @NonNull
        @Override
        public ListIterator<clsBeEmpresa> listIterator(int index) {
            return null;
        }

        @NonNull
        @Override
        public List<clsBeEmpresa> subList(int fromIndex, int toIndex) {
            return null;
        }
    };
}
